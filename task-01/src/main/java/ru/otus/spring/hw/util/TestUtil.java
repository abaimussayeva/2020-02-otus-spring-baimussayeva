package ru.otus.spring.hw.util;

import ru.otus.spring.hw.domain.model.Question;
import java.util.List;

public class TestUtil {

    public static int evaluate(List<String> answers, List<Question> questions) {
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
}
