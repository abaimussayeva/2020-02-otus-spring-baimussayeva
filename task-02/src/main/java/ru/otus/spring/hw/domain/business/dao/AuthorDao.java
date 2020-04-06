package ru.otus.spring.hw.domain.business.dao;

import ru.otus.spring.hw.domain.model.Author;

import java.util.List;

public interface AuthorDao {
    Author getById(long id);
    List<Author> getAll();
}
