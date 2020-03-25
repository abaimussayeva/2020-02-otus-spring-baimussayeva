package ru.otus.spring.hw.domain.application;

import ru.otus.spring.hw.domain.errors.QuestionLoadException;

public interface TestStartService {
    void startTest() throws QuestionLoadException;
}
