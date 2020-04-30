package ru.otus.spring.hw.application.business.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.otus.spring.hw.application.model.Book;
import ru.otus.spring.hw.application.model.Comment;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Spring Data Mongo для работы с комментариями должен ")
class CommentRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private BookRepository bookRepository;

    @Test
    @DisplayName(" возвращать 4 последних коммента к книге, и все 6 комментов из репозитория комменты")
    void getCommentsForBook() {
        Book book = bookRepository.findAll().get(0);
        assertThat(book.getComments()).hasSize(4)
                .allMatch(c -> !c.getComment().isEmpty())
                .allMatch(c -> c.getBookId().equals(book.getBookId()));
        List<Comment> comments = commentRepository.findByBookIdOrderByCreatedDesc(book.getBookId());
        assertThat(comments).hasSize(6)
                .allMatch(c -> !c.getComment().isEmpty())
                .allMatch(c -> c.getBookId().equals(book.getBookId()));
    }
}