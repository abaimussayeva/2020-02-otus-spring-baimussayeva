package ru.otus.spring.hw.domain.business.dao;

import ru.otus.spring.hw.domain.model.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreDao {
    Optional<Genre> getById(long id);
    List<Genre> getAll();
}
