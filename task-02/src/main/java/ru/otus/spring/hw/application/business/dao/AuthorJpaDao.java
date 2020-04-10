package ru.otus.spring.hw.application.business.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.hw.domain.business.dao.AuthorDao;
import ru.otus.spring.hw.domain.model.Author;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
@Repository
public class AuthorJpaDao implements AuthorDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<Author> getById(long id) {
        return Optional.ofNullable(em.find(Author.class, id));
    }

    @Override
    public List<Author> getAll() {
        return em.createQuery("select a from Author a", Author.class).getResultList();
    }
}
