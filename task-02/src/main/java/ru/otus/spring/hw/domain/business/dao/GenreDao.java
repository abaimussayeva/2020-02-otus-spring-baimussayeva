package ru.otus.spring.hw.domain.business.dao;

import ru.otus.spring.hw.domain.model.Genre;
import ru.otus.spring.hw.domain.model.dto.GenreDto;

import java.util.List;
import java.util.Optional;

public interface GenreDao {
    Optional<Genre> getById(long id);
    List<GenreDto> getAllFlat();
    List<GenreDto> getAllTree();
}
