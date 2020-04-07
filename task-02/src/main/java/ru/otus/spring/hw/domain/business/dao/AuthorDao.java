package ru.otus.spring.hw.domain.business.dao;

import ru.otus.spring.hw.domain.model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorDao {
    Optional<Author> getById(long id);
    List<Author> getAll();
}
