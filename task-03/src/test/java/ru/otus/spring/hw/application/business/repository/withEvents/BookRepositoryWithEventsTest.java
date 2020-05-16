package ru.otus.spring.hw.application.business.repository.withEvents;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import ru.otus.spring.hw.application.business.repository.AbstractRepositoryTest;
import ru.otus.spring.hw.application.business.repository.BookRepository;
import ru.otus.spring.hw.application.business.repository.CommentRepository;
import ru.otus.spring.hw.application.model.Author;
import ru.otus.spring.hw.application.model.Book;
import ru.otus.spring.hw.application.model.Genre;
import ru.otus.spring.hw.application.model.Lang;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("BookRepository при наличии listener-ов в контексте ")
@ComponentScan("ru.otus.spring.hw.application.events")
public class BookRepositoryWithEventsTest extends AbstractRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CommentRepository commentRepository;

    @DisplayName(" добавить книгу и вернуть полное описание книги")
    @Test
    void insert() {
        Book entity = new Book("Design Patterns", new Genre().setName("Java"),
                new Lang("Английский"),
                List.of(new Author("Erich Gamma"),
                        new Author("Richard Helm")));
        Book book = bookRepository.save(entity);
        assertThat(book.getBookId()).isNotEqualTo(0L);
        assertThat(book.getName()).isEqualTo("Design Patterns");
        assertThat(book.getAuthors().size()).isEqualTo(2);
        assertThat(book.getAuthors().get(0).getName()).isEqualTo("Erich Gamma");
        assertThat(book.getAuthors().get(1).getName()).isEqualTo("Richard Helm");
        assertThat(book.getGenre().getName()).isEqualTo("Java");
        assertThat(book.getLang().getName()).isEqualTo("Английский");
    }

    @DisplayName(" удалить книгу и удалить комментарии")
    @Test
    void deleteById() {
        String bookId = bookRepository.findAll().get(0).getBookId();
        bookRepository.deleteById(bookId);
        assertThat(bookRepository.findById(bookId)).isEmpty();
        assertThat(commentRepository.findByBookIdOrderByCreatedDesc(bookId)).isEmpty();
    }
}
