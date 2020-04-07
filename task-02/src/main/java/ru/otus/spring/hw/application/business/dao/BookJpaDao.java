package ru.otus.spring.hw.application.business.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ru.otus.spring.hw.domain.business.dao.BookDao;
import ru.otus.spring.hw.domain.model.Book;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public class BookJpaDao implements BookDao {

    @PersistenceContext
    private EntityManager em;

    public long count() {
        return em.createQuery(
                "select count(b) from Book b", Long.class).getSingleResult();
    }

    @Override
    public Book save(Book book) {
        if (book.getBookId() <= 0) {
            em.persist(book);
            return book;
        } else {
            return em.merge(book);
        }
    }

    @Override
    public Optional<Book> getById(long id) {
        return Optional.ofNullable(em.find(Book.class, id));
    }

    @Override
    public List<Book> getAll() {
        EntityGraph<?> entityGraph = em.getEntityGraph("book-entity-graph");
        TypedQuery<Book> query = em.createQuery("select b from Book b " +
                " join fetch b.genre " +
                " join fetch b.lang " +
                " join fetch b.authors", Book.class);
        query.setHint("javax.persistence.fetchgraph", entityGraph);
        return query.getResultList();
    }

    @Override
    public void deleteById(long id) {
        Query query = em.createQuery("delete " +
                "from Book b " +
                "where b.bookId = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public List<Book> searchByName(String search) {
        if (!StringUtils.hasText(search)) {
            return getAll();
        }
        EntityGraph<?> entityGraph = em.getEntityGraph("book-entity-graph");
        TypedQuery<Book> query = em.createQuery(
                "select b " +
                        " from Book b " +
                        " join fetch b.genre " +
                        " join fetch b.lang " +
                        " join fetch b.authors" +
                        " where lower(b.name) like lower(:search) "
                , Book.class);
        query.setHint("javax.persistence.fetchgraph", entityGraph);
        query.setParameter("search", "%" + search + "%");
        return query.getResultList();
    }
}
