package ru.otus.spring.hw.application.business.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.spring.hw.domain.model.Book;
import ru.otus.spring.hw.domain.model.Comment;

import javax.persistence.PersistenceException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Репозиторий на основе Jpa для работы с комментариями должен ")
@DataJpaTest
@Import({CommentJpaDao.class})
class CommentJpaDaoTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private CommentJpaDao dao;

    @DisplayName("Вернуть комментарий по Ид")
    @Test
    void getCommentById() {
        Comment comment = dao.getCommentById(1L).orElseThrow();
        assertThat(comment.getComment()).isEqualTo("Интересная книга");
    }

    @DisplayName("Добавить  комментарий ")
    @Test
    void addComment() {
        Book book = em.find(Book.class, 10L);
        Comment comment = new Comment(book.getBookId(), "Some new comment");
        comment = dao.addComment(comment);
        assertThat(comment.getBookId()).isNotEqualTo(0L);
    }

    @DisplayName("Вернуть ошибку о нарушении внешнего ключа ")
    @Test
    void addCommentWithConstaintError() {
        Book book = em.find(Book.class, 55L);
        Comment comment = new Comment(55L, "Some new comment");
        assertThrows(PersistenceException.class, () -> dao.addComment(comment));
    }

    @Test
    void getCommentsForBook() {
        List<Comment> comments = em.find(Book.class, 1L).getComments();
        assertThat(comments).hasSize(2)
                .allMatch(c -> !c.getComment().isEmpty())
                .allMatch(c -> c.getBookId() == 1L);
    }
}