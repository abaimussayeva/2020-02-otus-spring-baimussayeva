package ru.otus.spring.hw.domain.business.dao;

import ru.otus.spring.hw.domain.errors.QuestionLoadException;
import ru.otus.spring.hw.domain.model.Question;

import java.util.List;

public interface QuestionsDao {
    List<Question> getQuestions() throws QuestionLoadException;
}
