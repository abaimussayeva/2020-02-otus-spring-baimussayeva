package ru.otus.spring.hw.domain.business.dao;

import ru.otus.spring.hw.domain.model.Lang;

import java.util.List;

public interface LangDao {
    Lang getById(long id);
    List<Lang> getAll();
}

