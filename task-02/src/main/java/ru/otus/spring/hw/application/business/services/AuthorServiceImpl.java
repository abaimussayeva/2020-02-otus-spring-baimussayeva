package ru.otus.spring.hw.application.business.services;

import org.springframework.stereotype.Service;
import ru.otus.spring.hw.domain.business.dao.AuthorDao;
import ru.otus.spring.hw.domain.business.services.AuthorService;
import ru.otus.spring.hw.domain.errors.DBOperationException;
import ru.otus.spring.hw.domain.model.Author;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDao authorDao;

    public AuthorServiceImpl(AuthorDao authorDao) {
        this.authorDao = authorDao;
    }

    @Override
    public List<Author> getAuthors() throws DBOperationException {
        try {
            return authorDao.getAll();
        } catch (Exception e) {
            throw new DBOperationException("Authors get error", e);
        }
    }
}
