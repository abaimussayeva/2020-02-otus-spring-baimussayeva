package ru.otus.spring.hw.application.business.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mapping.MappingException;
import ru.otus.spring.hw.application.model.Author;
import ru.otus.spring.hw.application.model.Book;
import ru.otus.spring.hw.application.model.Genre;
import ru.otus.spring.hw.application.model.Lang;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Репозиторий на основе Spring Data Mongo для работы с книгами должно")
class BookRepositoryTest extends AbstractRepositoryTest{

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private CommentRepository commentRepository;

    @DisplayName(" вернуть изначально кол-во книг в бд")
    @Test
    void countTest() {
        long count = bookRepository.count();
        assertThat(count).isEqualTo(10L);
    }

    @DisplayName(" кинуть MappingException при записи с отсутствующими данными в БД")
    @Test
    void insert() {
        Book entity = new Book("Design Patterns", new Genre().setName("Java"),
                new Lang("Английский"),
                List.of(new Author("Erich Gamma"),
                        new Author("Richard Helm")));
        assertThatThrownBy(() -> bookRepository.save(entity)).isInstanceOf(MappingException.class);
    }

    @DisplayName(" вернуть книгу по ид")
    @Test
    void getById() {
        Book book = bookRepository.findAll().get(0);
        assertThat(book.getName()).isEqualTo("Джейн Эйр");
        assertThat(book.getAuthors().size()).isEqualTo(1);
        assertThat(book.getAuthors().get(0).getName()).isEqualTo("Шарлотта Бронте");
        assertThat(book.getGenre().getName()).isEqualTo("Проза");
        assertThat(book.getLang().getName()).isEqualTo("Русский");
    }

    @DisplayName(" вернуть пустую книгу из-за отсутствующего ид")
    @Test
    void getByIdNoSuchElement() {
        assertThat(bookRepository.findById("10000L").isEmpty());
    }

    @DisplayName(" вернуть список из 10 книг")
    @Test
    void getAll() {
        List<Book> books = bookRepository.findAll();
        assertThat(books).hasSize(10)
                .allMatch(s -> !s.getBookId().isEmpty())
                .allMatch(s -> !s.getName().equals(""))
                .allMatch(s -> s.getAuthors() != null && s.getAuthors().size() > 0)
                .allMatch(s -> s.getGenre() != null)
                .allMatch(s -> s.getLang() != null);
    }

    @DisplayName(" удалить книгу, но не удалить комментарии, из-за отсутствия листенеров")
    @Test
    void deleteById() {
        String bookId = bookRepository.findAll().get(0).getBookId();
        bookRepository.deleteById(bookId);
        assertThat(bookRepository.findById(bookId)).isEmpty();
        assertThat(commentRepository.findByBookIdOrderByCreatedDesc(bookId)).isNotEmpty();
    }

    @DisplayName(" найти книги с вхождением 'же'")
    @Test
    void searchByName() {
        List<Book> books = bookRepository.findByNameContainingIgnoreCase("же");
        assertThat(books.size()).isEqualTo(2);
        assertThat(books.get(0).getName()).isEqualTo("Джейн Эйр");
        assertThat(books.get(1).getName()).isEqualTo("Жестокий век");
    }

    @DisplayName(" вернуть пустой список книг")
    @Test
    void searchByNameNoBooksFound() {
        List<Book> books = bookRepository.findByNameContainingIgnoreCase("ghghghg");
        assertThat(books.size()).isEqualTo(0);
    }

    @DisplayName(" вернуть список из 2 книг")
    @Test
    void searchByName2books() {
        List<Book> books = bookRepository.findByNameContainingIgnoreCase("l");
        assertThat(books.size()).isEqualTo(2);
    }

    @DisplayName(" обновить книгу про паттерны")
    @Test
    void update() {
        Book book = bookRepository.findAll().get(9);
        book.setName("Design Patterns: Elements of Reusable Object-Oriented Software");
        book.getAuthors().remove(3);
        book.getAuthors().remove(2);
        book.setGenre(genreRepository.findByNameContains("Java").get(0));
        book = bookRepository.save(book);
        assertThat(book.getName()).isEqualTo("Design Patterns: Elements of Reusable Object-Oriented Software");
        assertThat(book.getAuthors().size()).isEqualTo(2);
        assertThat(book.getAuthors().get(0).getName()).isEqualTo("Erich Gamma");
        assertThat(book.getAuthors().get(1).getName()).isEqualTo("Richard Helm");
        assertThat(book.getGenre().getName()).isEqualTo("Java");
        assertThat(book.getLang().getName()).isEqualTo("Английский");
    }
}