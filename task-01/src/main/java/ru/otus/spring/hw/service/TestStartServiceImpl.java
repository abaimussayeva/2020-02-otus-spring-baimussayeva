package ru.otus.spring.hw.service;

import ru.otus.spring.hw.dao.QuestionsDao;
import ru.otus.spring.hw.domain.Person;
import ru.otus.spring.hw.domain.Question;
import ru.otus.spring.hw.service.login.SignInService;
import ru.otus.spring.hw.service.test.TestService;

import java.io.IOException;
import java.util.List;

public class TestStartServiceImpl implements TestStartService {

    private final QuestionsDao questionsDao;
    private final SignInService signInService;
    private final TestService testService;

    public TestStartServiceImpl(QuestionsDao questionsDao, SignInService signInService, TestService testService) {
        this.questionsDao = questionsDao;
        this.signInService = signInService;
        this.testService = testService;
    }

    @Override
    public void startTest() throws IOException {
        Person person = signInService.signIn();
        List<Question> questions = questionsDao.getQuestions();
        List<String> answers = testService.answerTheQuestions(questions);
        System.out.println(person.getFirstName() + " " + person.getLastName() + ", test is finished. ");
        System.out.println("Your score is " + testService.evaluate(answers, questions));
        System.out.println("Result analysis is below: ");
        System.out.println(testService.getPrintResult(answers, questions));
    }
}
