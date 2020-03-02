package ru.otus.spring.hw.service.test;

import ru.otus.spring.hw.domain.Question;

import java.util.List;

public interface TestService {

    List<String> answerTheQuestions(List<Question> questions);
    int evaluate(List<String> answers, List<Question> questions);
    String getPrintResult(List<String> answers, List<Question> questions);

}
