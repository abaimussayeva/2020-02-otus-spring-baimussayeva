package ru.otus.spring.hw.domain.business.test;

import ru.otus.spring.hw.domain.model.Question;
import ru.otus.spring.hw.util.OutColor;

import java.util.List;

public interface TestService {

    List<String> answerTheQuestions(List<Question> questions);

    default int evaluate(List<String> answers, List<Question> questions) {
        if (answers == null || questions == null) {
            throw new IllegalArgumentException("Parameters cannot be null");
        }
        if (answers.size() != questions.size()) {
            throw new IllegalArgumentException("Answers and questions list size must be equal");
        }
        int mark = 0;
        for (int i = 0; i < answers.size(); i ++) {
            if (answers.get(i).equals(questions.get(i).getAnswer())) {
                mark++;
            }
        }
        return mark;
    }

    default String getPrintResult(List<String> answers, List<Question> questions) {
        if (answers == null || questions == null) {
            throw new IllegalArgumentException("Parameters cannot be null");
        }
        if (answers.size() != questions.size()) {
            throw new IllegalArgumentException("Answers and questions list size must be equal");
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < answers.size(); i ++) {
            if (answers.get(i).equals(questions.get(i).getAnswer())) {
                builder.append(OutColor.ANSI_GREEN);
            } else {
                builder.append(OutColor.ANSI_RED);
            }
            builder.append(i+1).append(". ").append(questions.get(i).getPrintQuestion()).append("\n");
            builder.append("Correct is: ").append(questions.get(i).getAnswer());
            builder.append(", your answer is: ").append(answers.get(i)).append("\n");
        }
        return builder.toString();
    }
}
