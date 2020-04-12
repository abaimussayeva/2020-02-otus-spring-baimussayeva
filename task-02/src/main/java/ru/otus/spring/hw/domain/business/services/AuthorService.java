package ru.otus.spring.hw.domain.business.services;

import ru.otus.spring.hw.domain.errors.DBOperationException;
import ru.otus.spring.hw.domain.model.Author;

import java.util.List;

public interface AuthorService {
    List<Author> getAuthors() throws DBOperationException;
}
