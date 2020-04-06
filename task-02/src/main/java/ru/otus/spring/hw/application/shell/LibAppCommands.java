package ru.otus.spring.hw.application.shell;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.table.BeanListTableModel;
import org.springframework.shell.table.BorderStyle;
import org.springframework.shell.table.TableBuilder;
import org.springframework.shell.table.TableModel;
import org.springframework.util.StringUtils;
import ru.otus.spring.hw.domain.business.IOService;
import ru.otus.spring.hw.domain.business.l10n.L10nService;
import ru.otus.spring.hw.domain.business.services.BaseService;
import ru.otus.spring.hw.domain.errors.DBOperationException;
import ru.otus.spring.hw.domain.model.*;
import ru.otus.spring.hw.domain.model.dto.BookDto;
import ru.otus.spring.hw.domain.model.entity.BookEntity;
import ru.otus.spring.hw.util.EditType;

import java.util.*;
import java.util.stream.Collectors;

@ShellComponent
@RequiredArgsConstructor
public class LibAppCommands {

    private static final Logger LOGGER = LoggerFactory.getLogger(LibAppCommands.class);

    private final IOService ioService;
    private final BaseService baseService;
    private final L10nService l10nService;
    private final EditTypeUtil editTypeUtil;

    @ShellMethod(value = "Изменить локаль", key = {"locale"})
    public void locale() {
        String locale = ioService.selectFromList(l10nService.getMessage("locale"), l10nService.getMessage("choose"),
                l10nService.availableLocales(), null);
        l10nService.chooseLocale(locale);
    }

    @ShellMethod(value = "Показать все книги", key = {"books", "all"})
    public void allBooks() {
        List<BookDto> books;
        try {
            books = baseService.getAllBooks();
        } catch (DBOperationException e) {
            ioService.printError(e.getMessage());
            LOGGER.error(e.getMessage(), e);
            return;
        }
        LinkedHashMap<String, Object> headers = new LinkedHashMap<>();
        headers.put("bookId", l10nService.getMessage("book_id"));
        headers.put("name", l10nService.getMessage("book_name"));
        headers.put("authors", l10nService.getMessage("author"));
        headers.put("genre", l10nService.getMessage("genre"));
        headers.put("language", l10nService.getMessage("lang"));
        TableModel model = new BeanListTableModel<>(books, headers);
        TableBuilder tableBuilder = new TableBuilder(model);
        tableBuilder.addInnerBorder(BorderStyle.fancy_light);
        tableBuilder.addHeaderBorder(BorderStyle.fancy_double);
        ioService.out(tableBuilder.build().render(100));
    }

    @ShellMethod(value = "Поиск книг по названию", key = {"search"})
    public void search() {
        List<Book> books;
        try {
            String search = ioService.readString(l10nService.getMessage("search_book"));
            books = baseService.searchBookByName(search);
        } catch (DBOperationException e) {
            ioService.printError(e.getMessage());
            LOGGER.error(e.getMessage(), e);
            return;
        }
        books.forEach(book -> {
            ioService.out("------------------------");
            showBook(book.toDto());
        });
    }

    @ShellMethod(value = "Добавить книгу", key = {"add", "create", "addBook"})
    public void create() {
        try {
            String bookName = enterName();
            List<Long> bookAuthors = chooseAuthors();
            Long bookGenreId = chooseGenre();
            Long bookLangId = chooseLang();
            BookDto bookDto = baseService.addBook(new BookEntity(BookEntity.NEW_BOOK_ID,
                    bookName,
                    bookGenreId,
                    bookLangId,
                    bookAuthors));
            ioService.printSuccess(l10nService.getMessage("book_added"));
            showBook(bookDto);
        } catch (DBOperationException e) {
            ioService.printError(e.getMessage());
            LOGGER.error(e.getMessage(), e);
        }
    }

    @ShellMethod(value = "Удалить книгу", key = {"remove", "delete"})
    public void remove() {
        try {
            Optional<Book> book = chooseBook();
            if (book.isEmpty()) {
                return;
            }
            boolean delete = ioService.selectFromList(l10nService.getMessage("remove_book_confirm", book.get().getName()),
                    l10nService.getMessage("choose_y_n"),
                    Map.of(Messages.Y, l10nService.getMessage("yes"),
                           Messages.N, l10nService.getMessage("no")), null).equals(Messages.Y);
            if (delete) {
                baseService.removeBook(book.get().getBookId());
                ioService.printSuccess(l10nService.getMessage("book_removed"));
            } else {
                ioService.printWarning(l10nService.getMessage("book_not_removed"));
            }
        } catch (DBOperationException e) {
            ioService.printError(e.getMessage());
            LOGGER.error(e.getMessage(), e);
        }
    }

    @ShellMethod(value = "Редактировать книгу", key = {"edit"})
    public void edit() {
        try {
            Optional<Book> book = chooseBook();
            if (book.isEmpty()) {
                return;
            }
            Book current = book.get();
            String bookName = current.getName();
            Long bookGenreId = current.getGenre().getGenreId();
            Long bookLangId = current.getLang().getLangId();
            List<Long> bookAuthors = current.getAuthors().stream().map(Author::getAuthorId).collect(Collectors.toList());
            Map<String, String> editTypes = editTypeUtil.getEditTypeMap();
            boolean edit = true;
            while (edit) {
                String selectedType = ioService.selectFromList(l10nService.getMessage("choose_field_to_edit"),
                        l10nService.getMessage("choose_field_to_edit"), editTypes, null);
                EditType type = editTypeUtil.getTypeByOrder(selectedType);
                switch (type) {
                    case name:
                        bookName = enterName();
                        break;
                    case authors:
                        ioService.printWarning(l10nService.getMessage("author_warning"));
                        bookAuthors = chooseAuthors();
                        break;
                    case genre:
                        bookGenreId = chooseGenre();
                        break;
                    case lang:
                        bookLangId = chooseLang();
                        break;
                }
                editTypes.remove(String.valueOf(type.getOrder()));
                edit = !editTypes.isEmpty() &&
                        ioService.selectFromList(l10nService.getMessage("change_other_fields"), l10nService.getMessage("choose_y_n"),
                                Map.of(Messages.Y, l10nService.getMessage("yes"),
                                       Messages.N, l10nService.getMessage("no")), null).equals(Messages.Y);
            }
            boolean accept = ioService.selectFromList(l10nService.getMessage("edit_confirm"), l10nService.getMessage("choose_y_n"),
                    Map.of(Messages.Y, l10nService.getMessage("yes"),
                            Messages.N, l10nService.getMessage("no")), null).equals(Messages.Y);
            if (accept) {
                BookDto bookDto;
                bookDto = baseService.editBook(new BookEntity(current.getBookId(), bookName, bookGenreId, bookLangId, bookAuthors));
                ioService.printSuccess(l10nService.getMessage("changes_accepted"));
                showBook(bookDto);
            } else {
                ioService.printWarning(l10nService.getMessage("changes_not_accepted"));
            }
        } catch (DBOperationException e) {
            ioService.printError(e.getMessage());
            LOGGER.error(e.getMessage(), e);
        }
    }

    private void showBook(BookDto bookDto) {
        ioService.printKeyValue(l10nService.getMessage("book_id"), String.valueOf(bookDto.getBookId()));
        ioService.printKeyValue(l10nService.getMessage("book_name"), bookDto.getName());
        ioService.printKeyValue(l10nService.getMessage("author"), bookDto.getAuthors());
        ioService.printKeyValue(l10nService.getMessage("genre"), bookDto.getGenre());
        ioService.printKeyValue(l10nService.getMessage("lang"), bookDto.getLanguage());
    }

    private Optional<Book> chooseBook() throws DBOperationException {
        String search = ioService.readString(l10nService.getMessage("search_book"));
        List<Book> books = baseService.searchBookByName(search);
        if (books.isEmpty()) {
            ioService.printWarning(l10nService.getMessage("no_books"));
            return Optional.empty();
        }
        Map<Long, Book> bookMap = books.stream().collect(
                Collectors.toMap(Book::getBookId, g -> g));
        Long bookId = ioService.selectLongFromList(l10nService.getMessage("books"), l10nService.getMessage("choose_book"),
                books.stream().collect(
                        Collectors.toMap(Book::getBookId, Book::getName)), null);
        return Optional.of(bookMap.get(bookId));
    }

    private String enterName() {
        String bookName;
        do {
            bookName = ioService.readString(l10nService.getMessage("book_name"));
            if (!StringUtils.hasText(bookName)) {
                ioService.printWarning(l10nService.getMessage("enter_book_name"));
            }
        } while (bookName == null);
        return bookName;
    }

    private Long chooseLang() throws DBOperationException {
        List<Lang> langs = baseService.getLangs();
        return ioService.selectLongFromList(l10nService.getMessage("lang"), l10nService.getMessage("choose_lang"),
                langs.stream().collect(
                        Collectors.toMap(Lang::getLangId, Lang::getName)), null);
    }

    private Long chooseGenre() throws DBOperationException {
        Long bookGenreId;
        List<Genre> genres = baseService.getGenres();
        Map<Long, Genre> genreMap = genres.stream().collect(
                Collectors.toMap(Genre::getGenreId, g -> g));
        boolean chooseGenre;
        do {
            bookGenreId = ioService.selectLongFromList(l10nService.getMessage("genre"), l10nService.getMessage("choose_genre"),
                    genres.stream().collect(
                            Collectors.toMap(Genre::getGenreId, Genre::getName)), null);
            Genre selectedGenre = genreMap.get(bookGenreId);
            if (selectedGenre.hasChild()) {
                genres = selectedGenre.getChildren();
                genreMap = genres.stream().collect(
                        Collectors.toMap(Genre::getGenreId, g -> g));
                chooseGenre = ioService.selectFromList(l10nService.getMessage("precise_genre"), l10nService.getMessage("choose_y_n"),
                        Map.of(Messages.Y, l10nService.getMessage("yes"),
                               Messages.N, l10nService.getMessage("no")), null).equals(Messages.Y);
            } else {
                chooseGenre = false;
            }
        } while (chooseGenre);
        return bookGenreId;
    }

    private List<Long> chooseAuthors() throws DBOperationException {
        List<Long> bookAuthors = new ArrayList<>();
        List<Author> authors = baseService.getAuthors();
        boolean addAuthor = true;
        while (addAuthor) {
            Long authorId = ioService.selectLongFromList(l10nService.getMessage("author"), l10nService.getMessage("choose_author"),
                    authors.stream().collect(
                            Collectors.toMap(Author::getAuthorId, Author::getName)), null);
            bookAuthors.add(authorId);
            addAuthor = ioService.selectFromList(l10nService.getMessage("add_more_author"), l10nService.getMessage("choose_y_n"),
                    Map.of(Messages.Y, l10nService.getMessage("yes"),
                           Messages.N, l10nService.getMessage("no")), null).equals(Messages.Y);
            if (addAuthor) {
                authors = authors.stream()
                        .filter(author -> !bookAuthors.contains(author.getAuthorId()))
                        .collect(Collectors.toList());
            }
        }
        return bookAuthors;
    }
}

