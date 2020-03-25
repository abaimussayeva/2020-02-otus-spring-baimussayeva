package ru.otus.spring.hw.domain.business.test;

import ru.otus.spring.hw.domain.model.Question;

import java.util.List;

public interface TestService {
    List<String> answerTheQuestions(List<Question> questions);
    String getPrintResult(List<String> answers, List<Question> questions);
}
