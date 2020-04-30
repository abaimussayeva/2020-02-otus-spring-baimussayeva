package ru.otus.spring.hw.domain.business.services;

import ru.otus.spring.hw.application.model.Lang;
import ru.otus.spring.hw.domain.errors.DBOperationException;

import java.util.List;

public interface LangService {
    List<Lang> getLangs() throws DBOperationException;
}
