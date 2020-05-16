package ru.otus.spring.hw.application.business.services;

import org.springframework.stereotype.Service;
import ru.otus.spring.hw.application.business.repository.AuthorRepository;
import ru.otus.spring.hw.application.model.Author;
import ru.otus.spring.hw.domain.business.services.AuthorService;
import ru.otus.spring.hw.domain.errors.DBOperationException;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public List<Author> getAuthors() throws DBOperationException {
        try {
            return authorRepository.findAll();
        } catch (Exception e) {
            throw new DBOperationException("Authors get error", e);
        }
    }
}
