package ru.otus.spring.hw.application.business.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.hw.domain.business.dao.LangDao;
import ru.otus.spring.hw.domain.model.Lang;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
@Repository
public class LangJpaDao implements LangDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<Lang> getById(long id) {
        return Optional.ofNullable(em.find(Lang.class, id));
    }

    @Override
    public List<Lang> getAll() {
        return em.createQuery("select l from Lang l", Lang.class).getResultList();
    }
}
