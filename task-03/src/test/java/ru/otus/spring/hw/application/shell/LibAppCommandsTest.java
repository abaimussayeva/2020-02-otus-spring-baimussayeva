package ru.otus.spring.hw.application.shell;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.shell.Shell;
import ru.otus.spring.hw.application.business.repository.AuthorRepository;
import ru.otus.spring.hw.application.business.repository.BookRepository;
import ru.otus.spring.hw.application.business.repository.GenreRepository;
import ru.otus.spring.hw.application.business.repository.LangRepository;
import ru.otus.spring.hw.application.model.Author;
import ru.otus.spring.hw.application.model.Book;
import ru.otus.spring.hw.application.model.Genre;
import ru.otus.spring.hw.application.model.Lang;
import ru.otus.spring.hw.domain.business.IOService;
import ru.otus.spring.hw.domain.business.l10n.L10nService;
import ru.otus.spring.hw.domain.business.services.AuthorService;
import ru.otus.spring.hw.domain.business.services.BookService;
import ru.otus.spring.hw.domain.business.services.GenreService;
import ru.otus.spring.hw.domain.business.services.LangService;
import ru.otus.spring.hw.domain.dto.BookDto;
import ru.otus.spring.hw.domain.dto.GenreDto;
import ru.otus.spring.hw.domain.errors.DBOperationException;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@DisplayName("Тест команд shell ")
@SpringBootTest
class LibAppCommandsTest {

    @Autowired
    private Shell shell;

    @Autowired
    private L10nService l10nService;

    @Autowired
    private EditTypeUtil editTypeUtil;

    @MockBean
    private IOService ioService;

    @MockBean
    private BookService baseService;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private GenreService genreService;

    @MockBean
    private LangService langService;

    @MockBean
    private BookRepository bookDao;

    @MockBean
    private AuthorRepository authorDao;

    @MockBean
    private LangRepository langDao;

    @MockBean
    private GenreRepository genreDao;

    private static final String COMMAND_ALL = "all";
    private static final String COMMAND_ADD = "add";
    private static final String COMMAND_EDIT = "edit";
    private static final String COMMAND_SEARCH = "search";
    private static final String COMMAND_REMOVE = "remove";

    @DisplayName(" должен вызвать метод получения всех книг")
    @Test
    void allBooks() throws DBOperationException {
        shell.evaluate(() -> COMMAND_ALL);
        verify(baseService, times(1)).getAllBooks();
        verify(ioService, times(1)).out(anyString());
    }

    @DisplayName(" должен вызвать метод поиска книг")
    @Test
    void search() throws DBOperationException {
        when(ioService.readString(anyString()))
                .thenAnswer(mock -> "Поиск");
        when(baseService.searchBookByName("Поиск")).thenReturn(List.of(
                new Book("1", "name", new Genre("1","jenre"),
                        new Lang("1", "ru"))));
        shell.evaluate(() -> COMMAND_SEARCH);
        verify(baseService, times(1)).searchBookByName("Поиск");
        verify(ioService, times(2)).out(anyString());
        verify(ioService, times(4)).printKeyValue(anyString(), anyString());
    }

    @DisplayName(" должен вызвать метод создания книги")
    @Test
    void create() throws DBOperationException {
        when(authorService.getAuthors()).thenReturn(List.of(
                new Author("1", "Шарлотта Бронте", ""),
                new Author("2", "Энн Бронте", "")));
        when(genreService.getGenres()).thenReturn(List.of(
                new GenreDto("1", "Худ. лит-ра"),
                new GenreDto("2", "Наука")));
        when(langService.getLangs()).thenReturn(List.of(
                new Lang("1", "ru"),
                new Lang("2", "en")));
        when(ioService.readString(anyString())).thenAnswer(mock -> "Новая книга");
        when(ioService.selectFromList(anyString(), anyString(), anyMap(), eq(null)))
                .thenAnswer(mock -> "2")
                .thenAnswer(mock -> "0")
                .thenAnswer(mock -> "1")
                .thenAnswer(mock -> "1");
        when(baseService.save(any(Book.class))).thenReturn(new BookDto("1", "Новая книга", "Худ. лит-ра", "ru", "Энн Бронте"));
        shell.evaluate(() -> COMMAND_ADD);
        verify(baseService, times(1)).save(any(Book.class));
        verify(ioService, times(1)).printSuccess(l10nService.getMessage("book_added"));
        verify(ioService, times(4)).printKeyValue(anyString(), anyString());
    }

    @DisplayName(" должен вызвать метод удаления книги")
    @Test
    void remove() throws DBOperationException {
        when(ioService.readString(anyString()))
                .thenAnswer(mock -> "Джейн Эйр");
        when(baseService.searchBookByName("Джейн Эйр")).thenReturn(List.of(
                new Book("1", "Джейн Эйр", new Genre("1","jenre"), new Lang("1", "ru"))));
        when(ioService.selectFromList(anyString(), anyString(), anyMap(), eq(null)))
                .thenAnswer(mock -> "1")
                .thenAnswer(mock -> "1");
        shell.evaluate(() -> COMMAND_REMOVE);
        verify(baseService, times(1)).removeBook(anyString());
        verify(ioService, times(1)).printSuccess(l10nService.getMessage("book_removed"));
    }

    @DisplayName(" должен не должен вызвать удаление из-за пустого списка найденных книг")
    @Test
    void removeIfSearchResultIsEmpty() throws DBOperationException {
        when(ioService.readString(anyString()))
                .thenAnswer(mock -> "Джейн Эйр");
        when(baseService.searchBookByName("Джейн Эйр")).thenReturn(Collections.emptyList());
        shell.evaluate(() -> COMMAND_REMOVE);
        verify(baseService, times(0)).removeBook(anyString());
        verify(ioService, times(0)).printSuccess(l10nService.getMessage("book_removed"));
    }

    @DisplayName(" должен не должен вызвать удаление без подтверждения")
    @Test
    void removeIfDoNotConfirm() throws DBOperationException {
        when(ioService.readString(anyString()))
                .thenAnswer(mock -> "Джейн Эйр");
        when(baseService.searchBookByName("Джейн Эйр")).thenReturn(List.of(
                new Book("1", "Джейн Эйр", new Genre("1","jenre"), new Lang("1", "ru"))));
        when(ioService.selectFromList(anyString(), anyString(), anyMap(), eq(null)))
                .thenAnswer(mock -> "1")
                .thenAnswer(mock -> "0");
        shell.evaluate(() -> COMMAND_REMOVE);
        verify(baseService, times(0)).removeBook(anyString());
        verify(ioService, times(0)).printSuccess(l10nService.getMessage("book_removed"));
    }

    static Stream<Arguments> confirmYesNo() {
        return Stream.of(
                arguments("1", 1, 4, "Изменения сохранены"),
                arguments("0", 0, 0, "Изменения не сохранены")
        );
    }

    @DisplayName(" должен должен вызвать метод редактирования книги")
    @ParameterizedTest
    @MethodSource("confirmYesNo")
    void edit(String confirm, int callCount, int printCallCount, String message) throws DBOperationException {
        when(ioService.readString(anyString()))
                .thenAnswer(mock -> "Джейн Эйр");
        when(baseService.searchBookByName(anyString())).thenReturn(List.of(
                new Book("1", "Джейн Эйр", new Genre("1","jenre"), new Lang("1", "ru"))));
        when(authorService.getAuthors()).thenReturn(List.of(
                new Author("1", "Шарлотта Бронте", ""),
                new Author("2", "Энн Бронте", "")));
        when(genreService.getGenres()).thenReturn(List.of(
                new GenreDto("1", "Худ. лит-ра"),
                new GenreDto("2", "Наука")));
        when(langService.getLangs()).thenReturn(List.of(
                new Lang("1", "ru"),
                new Lang("2", "en")));
        when(ioService.readString(anyString())).thenAnswer(mock -> "Измененное название");
        when(ioService.selectFromList(anyString(), anyString(), anyMap(), eq(null)))
                .thenAnswer(mock -> "1") // выбор книги
                .thenAnswer(mock -> "1") //название
                .thenAnswer(mock -> "1") //след поле
                .thenAnswer(mock -> "2") // выбор автора
                .thenAnswer(mock -> "2") //автор
                .thenAnswer(mock -> "0") //один автор
                .thenAnswer(mock -> "1") //след поле
                .thenAnswer(mock -> "3") //жанр
                .thenAnswer(mock -> "1") // выбор жанра
                .thenAnswer(mock -> "1") //след поле
                .thenAnswer(mock -> "4") //язык
                .thenAnswer(mock -> "2") // выбор языка
                .thenAnswer(mock -> confirm) //подтверждение
                ;
        when(baseService.save(any(Book.class))).thenReturn(new BookDto("1", "Измененное название", "Худ. лит-ра", "en", "Энн Бронте"));
        shell.evaluate(() -> COMMAND_EDIT);
        verify(baseService, times(callCount)).save(any(Book.class));
        if (callCount == 1) {
            verify(ioService, times(1)).printSuccess(message);
        } else {
            verify(ioService, times(1)).printWarning(message);
        }
        verify(ioService, times(printCallCount)).printKeyValue(anyString(), anyString());
    }
}