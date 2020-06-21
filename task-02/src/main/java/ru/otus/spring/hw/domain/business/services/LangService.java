package ru.otus.spring.hw.domain.business.services;

import ru.otus.spring.hw.domain.business.dto.LangDto;
import ru.otus.spring.hw.domain.errors.DBOperationException;

import java.util.List;

public interface LangService {
    List<LangDto> getLangs() throws DBOperationException;
}
