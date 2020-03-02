package ru.otus.spring.hw.service.test;


import ru.otus.spring.hw.domain.Question;
import ru.otus.spring.hw.util.OutColor;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TestServiceConsole implements TestService {

    @Override
    public List<String> answerTheQuestions(List<Question> questions) {
        Scanner scanner = new Scanner(System.in);
        List<String> answers = new ArrayList<>();
        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            System.out.println(i + 1 + ". " + question.getPrintQuestion());
            answers.add(scanner.nextLine());
        }
        return answers;
    }

    @Override
    public int evaluate(List<String> answers, List<Question> questions) {
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

    @Override
    public String getPrintResult(List<String> answers, List<Question> questions) {
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
