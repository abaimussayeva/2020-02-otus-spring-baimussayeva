package ru.otus.spring.hw.application.business.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.spring.hw.domain.model.Author;
import ru.otus.spring.hw.domain.model.Book;
import ru.otus.spring.hw.domain.model.Genre;
import ru.otus.spring.hw.domain.model.Lang;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Jpa для работы с книгами должно")
@DataJpaTest
class BookRepositoryTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private BookRepository dao;

    @DisplayName(" вернуть изначально кол-во книг в бд")
    @Test
    void countTest() {
        long count = dao.count();
        assertThat(count).isEqualTo(10L);
    }

    @DisplayName(" добавить книгу и вернуть полное описание книги")
    @Test
    void insert() {
        Book entity = new Book("Design Patterns", new Genre(14L, "Java"),
                new Lang(2L, "Английский"),
                List.of(new Author(10L, "Erich Gamma", ""),
                        new Author(11L, "Richard Helm", "")));
        Book book = dao.save(entity);
        assertThat(book.getBookId()).isNotEqualTo(0L);
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
        Book book = dao.findById(1L).orElseThrow();
        assertThat(book.getName()).isEqualTo("Джейн Эйр");
        assertThat(book.getAuthors().size()).isEqualTo(1);
        assertThat(book.getAuthors().get(0).getName()).isEqualTo("Шарлотта Бронте");
        assertThat(book.getGenre().getName()).isEqualTo("Проза");
        assertThat(book.getLang().getName()).isEqualTo("Русский");
    }

    @DisplayName(" вернуть пустую книгу из-за отсутствующего ид")
    @Test
    void getByIdNoSuchElement() {
        assertThat(dao.findById(10000L).isEmpty());
    }

    @DisplayName(" вернуть список из 10 книг")
    @Test
    void getAll() {
        List<Book> books = dao.findAll();
        assertThat(books).hasSize(10)
                .allMatch(s -> s.getBookId() != 0L)
                .allMatch(s -> !s.getName().equals(""))
                .allMatch(s -> s.getAuthors() != null && s.getAuthors().size() > 0)
                .allMatch(s -> s.getGenre() != null)
                .allMatch(s -> s.getLang() != null);
    }

    @DisplayName(" удалить книгу")
    @Test
    void deleteById() {
        dao.deleteById(1L);
        em.flush();
        assertThat(em.find(Book.class, 1L)).isNull();
    }

    @DisplayName(" найти книги с вхождением 'же'")
    @Test
    void searchByName() {
        List<Book> books = dao.findByNameContainingIgnoreCase("же");
        assertThat(books.size()).isEqualTo(2);
        assertThat(books.get(0).getName()).isEqualTo("Джейн Эйр");
        assertThat(books.get(1).getName()).isEqualTo("Жестокий век");
    }

    @DisplayName(" вернуть пустой список книг")
    @Test
    void searchByNameNoBooksFound() {
        List<Book> books = dao.findByNameContainingIgnoreCase("ghghghg");
        assertThat(books.size()).isEqualTo(0);
    }

    @DisplayName(" вернуть список из 2 книг")
    @Test
    void searchByName2books() {
        List<Book> books = dao.findByNameContainingIgnoreCase("l");
        assertThat(books.size()).isEqualTo(2);
    }

    @DisplayName(" обновить книгу про паттерны")
    @Test
    void update() {
        Book book = dao.findById(10L).orElseThrow();
        book.setName("Design Patterns");
        book.getAuthors().remove(3);
        book.getAuthors().remove(2);
        book.setGenre(new Genre(14L, "Java"));
        book = dao.save(book);
        em.flush();//чтобы увидеть все запросы в бд
        assertThat(book.getName()).isEqualTo("Design Patterns");
        assertThat(book.getAuthors().size()).isEqualTo(2);
        assertThat(book.getAuthors().get(0).getName()).isEqualTo("Erich Gamma");
        assertThat(book.getAuthors().get(1).getName()).isEqualTo("Richard Helm");
        assertThat(book.getGenre().getName()).isEqualTo("Java");
        assertThat(book.getLang().getName()).isEqualTo("Английский");
    }
}