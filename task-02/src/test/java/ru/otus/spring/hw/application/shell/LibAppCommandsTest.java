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
import ru.otus.spring.hw.domain.business.IOService;
import ru.otus.spring.hw.domain.business.dao.AuthorDao;
import ru.otus.spring.hw.domain.business.dao.BookDao;
import ru.otus.spring.hw.domain.business.dao.GenreDao;
import ru.otus.spring.hw.domain.business.dao.LangDao;
import ru.otus.spring.hw.domain.business.l10n.L10nService;
import ru.otus.spring.hw.domain.business.services.BaseService;
import ru.otus.spring.hw.domain.errors.DBOperationException;
import ru.otus.spring.hw.domain.model.*;
import ru.otus.spring.hw.domain.model.dto.BookDto;
import ru.otus.spring.hw.domain.model.entity.BookEntity;

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
    private BaseService baseService;

    @MockBean
    private BookDao bookDao;

    @MockBean
    private AuthorDao authorDao;

    @MockBean
    private LangDao langDao;

    @MockBean
    private GenreDao genreDao;

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
                new Book(1L, "name", new Genre(1L,"jenre"), new Lang(1L, "ru"))));
        shell.evaluate(() -> COMMAND_SEARCH);
        verify(baseService, times(1)).searchBookByName("Поиск");
        verify(ioService, times(1)).out(anyString());
        verify(ioService, times(5)).printKeyValue(anyString(), anyString());
    }

    @DisplayName(" должен вызвать метод создания книги")
    @Test
    void create() throws DBOperationException {
        when(baseService.getAuthors()).thenReturn(List.of(
                new Author(1L, "Шарлотта Бронте", ""),
                new Author(2L, "Энн Бронте", "")));
        when(baseService.getGenres()).thenReturn(List.of(
                new Genre(1L, "Худ. лит-ра"),
                new Genre(2L, "Наука")));
        when(baseService.getLangs()).thenReturn(List.of(
                new Lang(1L, "ru"),
                new Lang(2L, "en")));
        when(ioService.readString(anyString())).thenAnswer(mock -> "Новая книга");
        when(ioService.selectLongFromList(anyString(), anyString(), anyMap(), eq(null)))
                .thenAnswer(mock -> 2L)
                .thenAnswer(mock -> 1L)
                .thenAnswer(mock -> 1L);
        when(ioService.selectFromList(anyString(), anyString(), anyMap(), eq(null)))
                .thenAnswer(mock -> "0");
        when(baseService.addBook(any(BookEntity.class))).thenReturn(new BookDto(1, "Новая книга", "Худ. лит-ра", "ru", "Энн Бронте"));
        shell.evaluate(() -> COMMAND_ADD);
        verify(baseService, times(1)).addBook(any(BookEntity.class));
        verify(ioService, times(1)).printSuccess(l10nService.getMessage("book_added"));
        verify(ioService, times(5)).printKeyValue(anyString(), anyString());
    }

    @DisplayName(" должен вызвать метод удаления книги")
    @Test
    void remove() throws DBOperationException {
        when(ioService.readString(anyString()))
                .thenAnswer(mock -> "Джейн Эйр");
        when(baseService.searchBookByName("Джейн Эйр")).thenReturn(List.of(
                new Book(1L, "Джейн Эйр", new Genre(1L,"jenre"), new Lang(1L, "ru"))));
        when(ioService.selectLongFromList(anyString(), anyString(), anyMap(), eq(null)))
                .thenAnswer(mock -> 1L);
        when(ioService.selectFromList(anyString(), anyString(), anyMap(), eq(null)))
                .thenAnswer(mock -> "1");
        shell.evaluate(() -> COMMAND_REMOVE);
        verify(baseService, times(1)).removeBook(anyLong());
        verify(ioService, times(1)).printSuccess(l10nService.getMessage("book_removed"));
    }

    @DisplayName(" должен не должен вызвать удаление из-за пустого списка найденных книг")
    @Test
    void removeIfSearchResultIsEmpty() throws DBOperationException {
        when(ioService.readString(anyString()))
                .thenAnswer(mock -> "Джейн Эйр");
        when(baseService.searchBookByName("Джейн Эйр")).thenReturn(Collections.emptyList());
        shell.evaluate(() -> COMMAND_REMOVE);
        verify(baseService, times(0)).removeBook(anyLong());
        verify(ioService, times(0)).printSuccess(l10nService.getMessage("book_removed"));
    }

    @DisplayName(" должен не должен вызвать удаление без подтверждения")
    @Test
    void removeIfDoNotConfirm() throws DBOperationException {
        when(ioService.readString(anyString()))
                .thenAnswer(mock -> "Джейн Эйр");
        when(baseService.searchBookByName("Джейн Эйр")).thenReturn(List.of(
                new Book(1L, "Джейн Эйр", new Genre(1L,"jenre"), new Lang(1L, "ru"))));
        when(ioService.selectLongFromList(anyString(), anyString(), anyMap(), eq(null)))
                .thenAnswer(mock -> 1L);
        when(ioService.selectFromList(anyString(), anyString(), anyMap(), eq(null)))
                .thenAnswer(mock -> "0");
        shell.evaluate(() -> COMMAND_REMOVE);
        verify(baseService, times(0)).removeBook(anyLong());
        verify(ioService, times(0)).printSuccess(l10nService.getMessage("book_removed"));
    }

    static Stream<Arguments> confirmYesNo() {
        return Stream.of(
                arguments("1", 1, 5, "Изменения сохранены"),
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
                new Book(1L, "Джейн Эйр", new Genre(1L,"jenre"), new Lang(1L, "ru"))));
        when(baseService.getAuthors()).thenReturn(List.of(
                new Author(1L, "Шарлотта Бронте", ""),
                new Author(2L, "Энн Бронте", "")));
        when(baseService.getGenres()).thenReturn(List.of(
                new Genre(1L, "Худ. лит-ра"),
                new Genre(2L, "Наука")));
        when(baseService.getLangs()).thenReturn(List.of(
                new Lang(1L, "ru"),
                new Lang(2L, "en")));
        when(ioService.readString(anyString())).thenAnswer(mock -> "Измененное название");
        when(ioService.selectLongFromList(anyString(), anyString(), anyMap(), eq(null)))
                .thenAnswer(mock -> 1L) // выбор книги
                .thenAnswer(mock -> 2L) // выбор автора
                .thenAnswer(mock -> 1L) // выбор жанра
                .thenAnswer(mock -> 2L) // выбор языка
                ;
        when(ioService.selectFromList(anyString(), anyString(), anyMap(), eq(null)))
                .thenAnswer(mock -> "1") //название
                .thenAnswer(mock -> "1") //след поле
                .thenAnswer(mock -> "2") //автор
                .thenAnswer(mock -> "0") //один автор
                .thenAnswer(mock -> "1") //след поле
                .thenAnswer(mock -> "3") //жанр
                .thenAnswer(mock -> "1") //след поле
                .thenAnswer(mock -> "4") //язык
                .thenAnswer(mock -> confirm) //подтверждение
                ;
        when(baseService.editBook(any(BookEntity.class))).thenReturn(new BookDto(1, "Измененное название", "Худ. лит-ра", "en", "Энн Бронте"));
        shell.evaluate(() -> COMMAND_EDIT);
        verify(baseService, times(callCount)).editBook(any(BookEntity.class));
        if (callCount == 1) {
            verify(ioService, times(1)).printSuccess(message);
        } else {
            verify(ioService, times(1)).printWarning(message);
        }
        verify(ioService, times(printCallCount)).printKeyValue(anyString(), anyString());
    }
}