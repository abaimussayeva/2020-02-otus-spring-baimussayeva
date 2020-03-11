package ru.otus.spring.hw.domain.business.dao;

import ru.otus.spring.hw.domain.model.Question;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public interface QuestionsDao {

    List<Question> getQuestions(Locale locale) throws IOException;

}
