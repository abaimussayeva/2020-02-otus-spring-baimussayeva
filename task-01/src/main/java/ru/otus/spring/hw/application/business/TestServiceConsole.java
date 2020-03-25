package ru.otus.spring.hw.application.business;


import org.springframework.stereotype.Service;
import ru.otus.spring.hw.domain.business.IOService;
import ru.otus.spring.hw.domain.business.l10n.L10nService;
import ru.otus.spring.hw.domain.business.test.TestService;
import ru.otus.spring.hw.domain.model.Question;
import ru.otus.spring.hw.util.PromptColor;

import java.util.ArrayList;
import java.util.List;

@Service
public class TestServiceConsole implements TestService {

    private final L10nService l10nService;
    private final IOService ioService;

    public TestServiceConsole(L10nService l10nService, IOService ioService) {
        this.l10nService = l10nService;
        this.ioService = ioService;
    }

    @Override
    public List<String> answerTheQuestions(List<Question> questions) {
        List<String> answers = new ArrayList<>();
        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            String answer = ioService.selectFromList(i + 1 + ". " + question.getQuestion(),
                    l10nService.getMessage("choose"), question.getAnswers(), null);
            answers.add(answer);
        }
        return answers;
    }

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
                builder.append(PromptColor.GREEN.getConsoleValue());
            } else {
                builder.append(PromptColor.RED.getConsoleValue());
            }
            builder.append(i+1).append(". ").append(questions.get(i).getPrintQuestion()).append("\n");
            builder.append(l10nService.getMessage("question_analysis", questions.get(i).getAnswer(), answers.get(i)));
            builder.append("\n");
        }
        return builder.toString();
    }
}
