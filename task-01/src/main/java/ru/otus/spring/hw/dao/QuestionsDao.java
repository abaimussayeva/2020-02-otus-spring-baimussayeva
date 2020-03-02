package ru.otus.spring.hw.dao;

import ru.otus.spring.hw.domain.Question;

import java.io.IOException;
import java.util.List;

public interface QuestionsDao {

    List<Question> getQuestions() throws IOException;

}
