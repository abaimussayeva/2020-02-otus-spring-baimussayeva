package ru.otus.spring.hw.domain.business.services;

import ru.otus.spring.hw.domain.errors.DBOperationException;
import ru.otus.spring.hw.domain.model.Lang;

import java.util.List;

public interface LangService {
    List<Lang> getLangs() throws DBOperationException;
}
