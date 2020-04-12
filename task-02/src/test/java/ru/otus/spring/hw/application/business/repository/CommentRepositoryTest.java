package ru.otus.spring.hw.application.business.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import ru.otus.spring.hw.domain.model.Comment;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Репозиторий на основе Jpa для работы с комментариями должен ")
@DataJpaTest
class CommentRepositoryTest {

    @Autowired
    private CommentRepository dao;

    @Autowired
    private BookRepository bookRepository;

    @DisplayName("Вернуть комментарий по Ид")
    @Test
    void getCommentById() {
        Comment comment = dao.findById(1L).orElseThrow();
        assertThat(comment.getComment()).isEqualTo("Интересная книга");
    }

    @DisplayName("Добавить  комментарий ")
    @Test
    void addComment() {
        Comment comment = new Comment(10L, "Some new comment");
        comment = dao.save(comment);
        assertThat(comment.getBookId()).isNotEqualTo(0L);
    }

    @DisplayName("Вернуть ошибку о нарушении внешнего ключа ")
    @Test
    void addCommentWithConstaintError() {
        Comment comment = new Comment(55L, "Some new comment");
        assertThrows(DataIntegrityViolationException.class, () -> dao.save(comment));
    }

    @Test
    void getCommentsForBook() {
        List<Comment> comments = bookRepository.getOne(1L).getComments();
        assertThat(comments).hasSize(2)
                .allMatch(c -> !c.getComment().isEmpty())
                .allMatch(c -> c.getBookId() == 1L);
    }
}