package ru.otus.spring.hw.application.business;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.spring.hw.domain.application.TestStartService;
import ru.otus.spring.hw.domain.business.dao.QuestionsDao;
import ru.otus.spring.hw.domain.business.login.SignInService;
import ru.otus.spring.hw.domain.business.test.TestService;
import ru.otus.spring.hw.domain.model.Person;
import ru.otus.spring.hw.domain.model.Question;

import java.io.IOException;
import java.util.List;

@Service
public class TestStartServiceConsole implements TestStartService {

    private final QuestionsDao questionsDao;
    private final SignInService signInService;
    private final TestService testService;
    private final String defaultLocale;
    private final MessageSource messageSource;

    public TestStartServiceConsole(QuestionsDao questionsDao, SignInService signInService, TestService testService,
                                   @Value("${default.locale}")String defaultLocale, MessageSource messageSource) {
        this.questionsDao = questionsDao;
        this.signInService = signInService;
        this.testService = testService;
        this.defaultLocale = defaultLocale;
        this.messageSource = messageSource;
    }

    @Override
    public void startTest() throws IOException {
        Person person = signInService.signIn(defaultLocale);
        List<Question> questions = questionsDao.getQuestions(person.getLocale());
        List<String> answers = testService.answerTheQuestions(questions);
        System.out.println(messageSource.getMessage("test_finished", new Object[]{person.getFirstName(), person.getLastName()}, person.getLocale()));
        System.out.println(messageSource.getMessage("score", new Object[]{testService.evaluate(answers, questions)}, person.getLocale()));
        System.out.println(messageSource.getMessage("analysis", new Object[]{}, person.getLocale()));
        System.out.println(testService.getPrintResult(answers, questions));
    }
}
