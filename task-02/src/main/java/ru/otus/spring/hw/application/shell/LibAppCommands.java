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
        headers.put("bookId", Messages.BOOK_ID);
        headers.put("name", Messages.BOOK_NAME);
        headers.put("authors", Messages.AUTHOR);
        headers.put("genre", Messages.GENRE);
        headers.put("language", Messages.LANG);
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
            String search = ioService.readString(Messages.SEARCH_BOOK);
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
            ioService.printSuccess(Messages.BOOK_ADDED);
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
            boolean delete = ioService.selectFromList(String.format(Messages.REMOVE_BOOK_CONFIRM, book.get().getName()), Messages.CHOOSE_Y_N,
                    Map.of(Messages.Y, Messages.YES, Messages.N, Messages.NO), null).equals(Messages.Y);
            if (delete) {
                baseService.removeBook(book.get().getBookId());
                ioService.printSuccess(Messages.BOOK_REMOVED);
            } else {
                ioService.printWarning(Messages.BOOK_NOT_REMOVED);
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
            Map<String, String> editTypes = EditType.getEditTypeMap();
            boolean edit = true;
            while (edit) {
                String selectedType = ioService.selectFromList(Messages.CHOOSE_FIELD_TO_EDIT,
                        Messages.CHOOSE_FIELD_TO_EDIT, editTypes, null);
                EditType type = EditType.getTypeByOrder(selectedType);
                switch (type) {
                    case name:
                        bookName = enterName();
                        break;
                    case authors:
                        ioService.printWarning(Messages.AUTHORS_WARNING);
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
                        ioService.selectFromList(Messages.CHANGE_OTHER_FIELDS, Messages.CHOOSE_Y_N,
                                Map.of(Messages.Y, Messages.YES, Messages.N, Messages.NO), null).equals(Messages.Y);
            }
            boolean accept = ioService.selectFromList(Messages.EDIT_COMFIRM, Messages.CHOOSE_Y_N,
                    Map.of(Messages.Y, Messages.YES, Messages.N, Messages.NO), null).equals(Messages.Y);
            if (accept) {
                BookDto bookDto;
                bookDto = baseService.editBook(new BookEntity(current.getBookId(), bookName, bookGenreId, bookLangId, bookAuthors));
                ioService.printSuccess(Messages.CHANGES_ACCEPTED);
                showBook(bookDto);
            } else {
                ioService.printWarning(Messages.CHANGES_NOT_ACCEPTED);
            }
        } catch (DBOperationException e) {
            ioService.printError(e.getMessage());
            LOGGER.error(e.getMessage(), e);
        }
    }

    private void showBook(BookDto bookDto) {
        ioService.printKeyValue(Messages.BOOK_ID, String.valueOf(bookDto.getBookId()));
        ioService.printKeyValue(Messages.BOOK_NAME, bookDto.getName());
        ioService.printKeyValue(Messages.AUTHOR, bookDto.getAuthors());
        ioService.printKeyValue(Messages.GENRE, bookDto.getGenre());
        ioService.printKeyValue(Messages.LANG, bookDto.getLanguage());
    }

    private Optional<Book> chooseBook() throws DBOperationException {
        String search = ioService.readString(Messages.SEARCH_BOOK);
        List<Book> books = baseService.searchBookByName(search);
        if (books.isEmpty()) {
            ioService.printWarning(Messages.NO_BOOKS);
            return Optional.empty();
        }
        Map<Long, Book> bookMap = books.stream().collect(
                Collectors.toMap(Book::getBookId, g -> g));
        Long bookId = ioService.selectLongFromList(Messages.BOOKS, Messages.CHOOSE_BOOK,
                books.stream().collect(
                        Collectors.toMap(Book::getBookId, Book::getName)), null);
        return Optional.of(bookMap.get(bookId));
    }

    private String enterName() {
        String bookName;
        do {
            bookName = ioService.readString(Messages.BOOK_NAME);
            if (!StringUtils.hasText(bookName)) {
                ioService.printWarning(Messages.ENTER_BOOK_NAME);
            }
        } while (bookName == null);
        return bookName;
    }

    private Long chooseLang() throws DBOperationException {
        List<Lang> langs = baseService.getLangs();
        return ioService.selectLongFromList(Messages.LANG, Messages.CHOOSE_LANG,
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
            bookGenreId = ioService.selectLongFromList(Messages.GENRE, Messages.CHOOSE_GENRE,
                    genres.stream().collect(
                            Collectors.toMap(Genre::getGenreId, Genre::getName)), null);
            Genre selectedGenre = genreMap.get(bookGenreId);
            if (selectedGenre.hasChild()) {
                genres = selectedGenre.getChildren();
                genreMap = genres.stream().collect(
                        Collectors.toMap(Genre::getGenreId, g -> g));
                chooseGenre = ioService.selectFromList(Messages.PRECISE_GENRE, Messages.CHOOSE_Y_N,
                        Map.of(Messages.Y, Messages.YES, Messages.N, Messages.NO), null).equals(Messages.Y);
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
            Long authorId = ioService.selectLongFromList(Messages.AUTHOR, Messages.CHOOSE_AUTHOR,
                    authors.stream().collect(
                            Collectors.toMap(Author::getAuthorId, Author::getName)), null);
            bookAuthors.add(authorId);
            addAuthor = ioService.selectFromList(Messages.ADD_MORE_AUTHOR, Messages.CHOOSE_Y_N,
                    Map.of(Messages.Y, Messages.YES, Messages.N, Messages.NO), null).equalsIgnoreCase(Messages.Y);
            if (addAuthor) {
                authors = authors.stream()
                        .filter(author -> !bookAuthors.contains(author.getAuthorId()))
                        .collect(Collectors.toList());
            }
        }
        return bookAuthors;
    }
}

