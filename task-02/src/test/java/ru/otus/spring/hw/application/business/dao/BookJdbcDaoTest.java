package ru.otus.spring.hw.application.business.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.spring.hw.domain.business.IOService;
import ru.otus.spring.hw.domain.business.services.BaseService;
import ru.otus.spring.hw.domain.model.Book;
import ru.otus.spring.hw.domain.model.entity.BookEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Dao для работы с книгами должно")
@JdbcTest
@Import({BookJdbcDao.class, AuthorJdbcDao.class, GenreJdbcDao.class, LangJdbcDao.class})
class BookJdbcDaoTest {

    @Autowired
    private BookJdbcDao dao;

    @Autowired
    private AuthorJdbcDao authorDao;

    @Autowired
    private GenreJdbcDao genreDao;

    @Autowired
    private LangJdbcDao langDao;

    @MockBean
    private IOService ioService;

    @MockBean
    private BaseService baseService;

    @DisplayName(" вернуть изначально кол-во книг в бд")
    @Test
    void countTest() {
        int count = dao.count();
        assertThat(count).isEqualTo(10);
    }

    @DisplayName(" добавить книгу и вернуть полное описание книги")
    @Test
    void insert() {
        BookEntity entity = new BookEntity(BookEntity.NEW_BOOK_ID, "Design Patterns", 14L, 2L, List.of(10L, 11L));
        Book book = dao.insert(entity);
        assertThat(dao.count()).isEqualTo(11);
        assertThat(book.getName()).isEqualTo("Design Patterns");
        assertThat(book.getAuthors().size()).isEqualTo(2);
        assertThat(book.getAuthors().get(0).getName()).isEqualTo("Erich Gamma");
        assertThat(book.getAuthors().get(1).getName()).isEqualTo("Richard Helm");
        assertThat(book.getGenre().getName()).isEqualTo("Java");
        assertThat(book.getLang().getName()).isEqualTo("Английский");
    }

    @DisplayName(" вернуть книгу по ид")
    @Test
    void getById() {
        Book book = dao.getById(1L);
        assertThat(book.getName()).isEqualTo("Джейн Эйр");
        assertThat(book.getAuthors().size()).isEqualTo(1);
        assertThat(book.getAuthors().get(0).getName()).isEqualTo("Шарлотта Бронте");
        assertThat(book.getGenre().getName()).isEqualTo("Проза");
        assertThat(book.getLang().getName()).isEqualTo("Русский");
    }

    @DisplayName(" вернуть пустую книгу из-за отсутствующего ид")
    @Test
    void getByIdNoSuchElement() {
        assertThrows(EmptyResultDataAccessException.class, () -> dao.getById(10000L));
    }

    @DisplayName(" вернуть список из 10 книг")
    @Test
    void getAll() {
        assertThat(dao.getAll().size()).isEqualTo(10);
    }

    @DisplayName(" удалить книгу")
    @Test
    void deleteById() {
        Book book = dao.getById(1L);
        dao.deleteById(book.getBookId());
        assertThat(dao.count()).isEqualTo(9);
        assertThrows(EmptyResultDataAccessException.class, () -> dao.getById(10000L));
    }

    @DisplayName(" найти книга с вхождением 'же'")
    @Test
    void searchByName() {
        List<Book> books = dao.searchByName("же");
        assertThat(books.size()).isEqualTo(2);
        assertThat(books.get(0).getName()).isEqualTo("Джейн Эйр");
        assertThat(books.get(1).getName()).isEqualTo("Жестокий век");
    }

    @DisplayName(" обновить книгу про паттерны")
    @Test
    void update() {
        Book book = dao.getById(10L);
        book = dao.update(new BookEntity(book.getBookId(), "Design Patterns", 14L, 2L, List.of(10L, 11L)));
        assertThat(book.getName()).isEqualTo("Design Patterns");
        assertThat(book.getAuthors().size()).isEqualTo(2);
        assertThat(book.getAuthors().get(0).getName()).isEqualTo("Erich Gamma");
        assertThat(book.getAuthors().get(1).getName()).isEqualTo("Richard Helm");
        assertThat(book.getGenre().getName()).isEqualTo("Java");
        assertThat(book.getLang().getName()).isEqualTo("Английский");
    }
}