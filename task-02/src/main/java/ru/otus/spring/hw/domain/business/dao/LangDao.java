package ru.otus.spring.hw.domain.business.dao;

import ru.otus.spring.hw.domain.model.Lang;

import java.util.List;
import java.util.Optional;

public interface LangDao {
    Optional<Lang> getById(long id);
    List<Lang> getAll();
}

