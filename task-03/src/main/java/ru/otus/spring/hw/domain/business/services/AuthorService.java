package ru.otus.spring.hw.domain.business.services;

import ru.otus.spring.hw.application.model.Author;
import ru.otus.spring.hw.domain.errors.DBOperationException;

import java.util.List;

public interface AuthorService {
    List<Author> getAuthors() throws DBOperationException;
}
