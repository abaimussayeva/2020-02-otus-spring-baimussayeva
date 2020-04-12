package ru.otus.spring.hw.domain.business.services;

import ru.otus.spring.hw.domain.errors.DBOperationException;
import ru.otus.spring.hw.domain.model.Author;
import ru.otus.spring.hw.domain.model.Book;
import ru.otus.spring.hw.domain.model.Comment;
import ru.otus.spring.hw.domain.model.Lang;
import ru.otus.spring.hw.domain.model.dto.GenreDto;

import java.util.List;

public interface LangService {
    List<Lang> getLangs() throws DBOperationException;
}
