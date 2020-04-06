package ru.otus.spring.hw.domain.business.dao;

import ru.otus.spring.hw.domain.model.Genre;

import java.util.List;

public interface GenreDao {
    Genre getById(long id);
    List<Genre> getAll();
}
