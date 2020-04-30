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
import ru.otus.spring.hw.application.model.*;
import ru.otus.spring.hw.domain.business.IOService;
import ru.otus.spring.hw.domain.business.l10n.L10nService;
import ru.otus.spring.hw.domain.business.services.*;
import ru.otus.spring.hw.domain.dto.BookDto;
import ru.otus.spring.hw.domain.dto.GenreDto;
import ru.otus.spring.hw.domain.dto.HasName;
import ru.otus.spring.hw.domain.errors.DBOperationException;
import ru.otus.spring.hw.util.EditType;
import ru.otus.spring.hw.util.Messages;

import java.text.DateFormat;
import java.util.*;
import java.util.stream.Collectors;

@ShellComponent
@RequiredArgsConstructor
public class LibAppCommands {

    private static final Logger LOGGER = LoggerFactory.getLogger(LibAppCommands.class);

    private final IOService ioService;
    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final LangService langService;
    private final CommentService commentService;
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
            books = bookService.getAllBooks();
        } catch (DBOperationException e) {
            ioService.printError(e.getMessage());
            LOGGER.error(e.getMessage(), e);
            return;
        }
        LinkedHashMap<String, Object> headers = new LinkedHashMap<>();
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
            books = bookService.searchBookByName(search);
        } catch (DBOperationException e) {
            ioService.printError(e.getMessage());
            LOGGER.error(e.getMessage(), e);
            return;
        }
        books.forEach(book -> {
            ioService.out("------------------------");
            printWithComments(book, false);
        });
    }

    @ShellMethod(value = "Добавить книгу", key = {"add", "create", "addBook"})
    public void create() {
        try {
            String bookName = enterName();
            List<Author> bookAuthors = chooseAuthors();
            GenreDto genre = chooseGenre();
            Lang lang = chooseLang();
            BookDto bookDto = bookService.save(new Book(bookName,
                    new Genre(genre.getGenreId(), genre.getName()),
                    lang,
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
                bookService.removeBook(book.get().getBookId());
                ioService.printSuccess(l10nService.getMessage("book_removed"));
            } else {
                ioService.printWarning(l10nService.getMessage("book_not_removed"));
            }
        } catch (Exception e) {
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
            Map<String, String> editTypes = editTypeUtil.getEditTypeMap();
            boolean edit = true;
            while (edit) {
                String selectedType = ioService.selectFromList(l10nService.getMessage("choose_field_to_edit"),
                        l10nService.getMessage("choose_field_to_edit"), editTypes, null);
                EditType type = editTypeUtil.getTypeByOrder(selectedType);
                switch (type) {
                    case name:
                        current.setName(enterName());
                        break;
                    case authors:
                        ioService.printWarning(l10nService.getMessage("author_warning"));
                        current.setAuthors(chooseAuthors());
                        break;
                    case genre:
                        GenreDto genreDto = chooseGenre();
                        current.setGenre(new Genre(genreDto.getGenreId(), genreDto.getName()));
                        break;
                    case lang:
                        current.setLang(chooseLang());
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
                BookDto bookDto = bookService.save(current);
                ioService.printSuccess(l10nService.getMessage("changes_accepted"));
                showBook(bookDto);
            } else {
                ioService.printWarning(l10nService.getMessage("changes_not_accepted"));
            }
        } catch (Exception e) {
            ioService.printError(e.getMessage());
            LOGGER.error(e.getMessage(), e);
        }
    }

    @ShellMethod(value = "Добавить комментарий", key = {"addComment"})
    public void addComment() {
        try {
            Optional<Book> book = chooseBook();
            if (book.isEmpty()) {
                return;
            }
            Book current = bookService.getBookById(book.orElseThrow().getBookId());
            String comment = ioService.readString(l10nService.getMessage("enter_comment"));
            current = commentService.addComment(current, comment);
            printWithComments(current,true);
        } catch (Exception e) {
            ioService.printError(e.getMessage());
            LOGGER.error(e.getMessage(), e);
        }
    }

    @ShellMethod(value = "Показать комментарии к книге", key = {"comments"})
    public void printWithComments() {
        try {
            Optional<Book> book = chooseBook();
            if (book.isEmpty()) {
                return;
            }
            printWithComments(book.get(), true);
        } catch (Exception e) {
            ioService.printError(e.getMessage());
            LOGGER.error(e.getMessage(), e);
        }
    }

    private void printWithComments(Book current, boolean loadAll) {
        showBook(BookDto.fromBook(current));
        ioService.out(l10nService.getMessage("comments") + ": ");
        List<Comment> comments;
        if (loadAll) {
            comments = commentService.getBookComments(current.getBookId());
        } else {
            comments = current.getComments();
        }
        if (comments == null) {
            return;
        }
        for (Comment comment: comments) {
            ioService.printKeyValue("   " + DateFormat.getDateTimeInstance().format(comment.getCreated()), comment.getComment());
        }
    }

    private void showBook(BookDto bookDto) {
        ioService.printKeyValue(l10nService.getMessage("book_name"), bookDto.getName());
        ioService.printKeyValue(l10nService.getMessage("author"), bookDto.getAuthors());
        ioService.printKeyValue(l10nService.getMessage("genre"), bookDto.getGenre());
        ioService.printKeyValue(l10nService.getMessage("lang"), bookDto.getLanguage());
    }

    private Optional<Book> chooseBook() throws Exception {
        String search = ioService.readString(l10nService.getMessage("search_book"));
        List<Book> books = bookService.searchBookByName(search);
        if (books.isEmpty()) {
            ioService.printWarning(l10nService.getMessage("no_books"));
            return Optional.empty();
        }
        Map<String, Book> bookMap = getMapFromList(books);
        String bookId = ioService.selectFromList(l10nService.getMessage("books"), l10nService.getMessage("choose_book"),
                getMapStringFromList(books), null);
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

    private Lang chooseLang() throws DBOperationException {
        List<Lang> langs = langService.getLangs();
        Map<String, Lang> langMap = getMapFromList(langs);
        String selected = ioService.selectFromList(l10nService.getMessage("lang"), l10nService.getMessage("choose_lang"),
                getMapStringFromList(langs), null);
        return langMap.get(selected);
    }

    private GenreDto chooseGenre() throws DBOperationException {
        GenreDto genre;
        List<GenreDto> genres = genreService.getGenres();
        Map<String, GenreDto> genreMap = getMapFromList(genres);
        boolean chooseGenre;
        do {
            String selected = ioService.selectFromList(l10nService.getMessage("genre"), l10nService.getMessage("choose_genre"),
                    getMapStringFromList(genres), null);
            genre = genreMap.get(selected);
            if (!genre.getChildGenres().isEmpty()) {
                genres = genre.getChildGenres();
                genreMap = getMapFromList(genres);
                chooseGenre = ioService.selectFromList(l10nService.getMessage("precise_genre"), l10nService.getMessage("choose_y_n"),
                        Map.of(Messages.Y, l10nService.getMessage("yes"),
                               Messages.N, l10nService.getMessage("no")), null).equals(Messages.Y);
            } else {
                chooseGenre = false;
            }
        } while (chooseGenre);
        return genre;
    }

    private List<Author> chooseAuthors() throws DBOperationException {
        List<Author> bookAuthors = new ArrayList<>();
        List<Author> authors = authorService.getAuthors();
        Map<String, Author> authorMap = getMapFromList(authors);

        boolean addAuthor = true;
        while (addAuthor) {
            String authorId = ioService.selectFromList(l10nService.getMessage("author"), l10nService.getMessage("choose_author"),
                    getMapStringFromList(authors), null);
            bookAuthors.add(authorMap.get(authorId));
            addAuthor = ioService.selectFromList(l10nService.getMessage("add_more_author"), l10nService.getMessage("choose_y_n"),
                    Map.of(Messages.Y, l10nService.getMessage("yes"),
                           Messages.N, l10nService.getMessage("no")), null).equals(Messages.Y);
            if (addAuthor) {
                authors = authors.stream()
                        .filter(author -> !bookAuthors.contains(author))
                        .collect(Collectors.toList());
                authorMap = getMapFromList(authors);
            }
        }
        return bookAuthors;
    }

    private <T> Map<String, T> getMapFromList(List<T> objects) {
        return objects.stream()
                .collect(HashMap::new, (h, o) -> h.put(String.valueOf(h.size() + 1), o), (h, o) -> {
                });
    }

    private Map<String, String> getMapStringFromList(List<? extends HasName> objects) {
        return objects.stream()
                .collect(HashMap::new, (h, o) -> h.put(String.valueOf(h.size() + 1), o.getName()), (h, o) -> {
                });
    }
}

