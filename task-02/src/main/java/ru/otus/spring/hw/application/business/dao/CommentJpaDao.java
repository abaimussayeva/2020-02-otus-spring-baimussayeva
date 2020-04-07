package ru.otus.spring.hw.application.business.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.hw.domain.business.dao.CommentDao;
import ru.otus.spring.hw.domain.model.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public class CommentJpaDao implements CommentDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<Comment> getCommentById(long id) {
        return Optional.ofNullable(em.find(Comment.class, id));
    }

    @Override
    public Comment addComment(Comment comment) {
        em.persist(comment);
        return em.find(Comment.class, comment.getCommentId());
    }

    @Override
    public List<Comment> getCommentsForBook(long bookId) {
        TypedQuery<Comment> query = em.createQuery(
                "select c from Comment c where c.bookId = :bookId", Comment.class);
        query.setParameter("bookId", bookId);
        return query.getResultList();
    }
}
