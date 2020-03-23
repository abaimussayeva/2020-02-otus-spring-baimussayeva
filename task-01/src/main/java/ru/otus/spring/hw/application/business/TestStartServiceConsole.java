package ru.otus.spring.hw.application.business;

import org.springframework.stereotype.Service;
import ru.otus.spring.hw.domain.application.TestStartService;
import ru.otus.spring.hw.domain.business.IOService;
import ru.otus.spring.hw.domain.business.dao.QuestionsDao;
import ru.otus.spring.hw.domain.business.l10n.L10nService;
import ru.otus.spring.hw.domain.business.login.SignInService;
import ru.otus.spring.hw.domain.business.test.TestService;
import ru.otus.spring.hw.domain.errors.QuestionLoadException;
import ru.otus.spring.hw.domain.model.Person;
import ru.otus.spring.hw.domain.model.Question;
import ru.otus.spring.hw.util.TestUtil;
import java.util.List;

@Service
public class TestStartServiceConsole implements TestStartService {

    private final QuestionsDao questionsDao;
    private final SignInService signInService;
    private final TestService testService;
    private final L10nService l10nService;
    private final IOService ioService;

    public TestStartServiceConsole(QuestionsDao questionsDao, SignInService signInService,
                                   TestService testService, L10nService l10nService, IOService ioService) {
        this.questionsDao = questionsDao;
        this.signInService = signInService;
        this.testService = testService;
        this.l10nService = l10nService;
        this.ioService = ioService;
    }

    @Override
    public void startTest() throws QuestionLoadException {
        l10nService.chooseLocale();
        Person person = signInService.signIn();
        List<Question> questions = questionsDao.getQuestions();
        List<String> answers = testService.answerTheQuestions(questions);
        ioService.out(l10nService.getMessage("test_finished", person.getFirstName(), person.getLastName()));
        ioService.out(l10nService.getMessage("score", TestUtil.evaluate(answers, questions)));
        ioService.out(l10nService.getMessage("analysis"));
        ioService.out(testService.getPrintResult(answers, questions));
    }
}
