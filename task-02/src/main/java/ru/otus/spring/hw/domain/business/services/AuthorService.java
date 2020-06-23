package ru.otus.spring.hw.domain.business.services;

import ru.otus.spring.hw.domain.business.dto.AuthorDto;
import ru.otus.spring.hw.domain.errors.DBOperationException;

import java.util.List;

public interface AuthorService {
    List<AuthorDto> getAuthors() throws DBOperationException;
}
