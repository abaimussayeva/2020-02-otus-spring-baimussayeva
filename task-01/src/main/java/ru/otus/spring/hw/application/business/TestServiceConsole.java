package ru.otus.spring.hw.application.business;


import org.springframework.stereotype.Service;
import ru.otus.spring.hw.domain.business.test.TestService;
import ru.otus.spring.hw.domain.model.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
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
}
