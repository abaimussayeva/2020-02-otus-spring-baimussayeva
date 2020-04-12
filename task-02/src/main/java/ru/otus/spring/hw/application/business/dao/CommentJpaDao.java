package ru.otus.spring.hw.application.business.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.hw.domain.business.dao.CommentDao;
import ru.otus.spring.hw.domain.model.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Transactional(readOnly = true)
@Repository
public class CommentJpaDao implements CommentDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<Comment> getCommentById(long id) {
        return Optional.ofNullable(em.find(Comment.class, id));
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Comment addComment(Comment comment) {
        em.persist(comment);
        return em.find(Comment.class, comment.getCommentId());
    }
}
