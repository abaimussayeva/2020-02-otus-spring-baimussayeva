package ru.otus.spring.hw.domain.business.services;

import ru.otus.spring.hw.domain.dto.GenreDto;
import ru.otus.spring.hw.domain.errors.DBOperationException;

import java.util.List;

public interface GenreService {
    List<GenreDto> getGenres() throws DBOperationException;
}
