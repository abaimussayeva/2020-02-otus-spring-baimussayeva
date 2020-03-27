package ru.otus.spring.hw.application.business;

import org.springframework.stereotype.Service;
import ru.otus.spring.hw.domain.application.TestStartService;
import ru.otus.spring.hw.domain.business.test.BaseService;
import ru.otus.spring.hw.domain.business.dao.QuestionsDao;
import ru.otus.spring.hw.domain.business.test.TestService;
import ru.otus.spring.hw.domain.errors.QuestionLoadException;
import ru.otus.spring.hw.domain.model.Question;
import ru.otus.spring.hw.util.TestUtil;

import java.util.List;

@Service
public class TestStartServiceConsole implements TestStartService {

    private final BaseService baseTestService;
    private final QuestionsDao questionsDao;
    private final TestService testService;

    public TestStartServiceConsole(BaseService baseTestService, QuestionsDao questionsDao,
                                   TestService testService) {
        this.baseTestService = baseTestService;
        this.questionsDao = questionsDao;
        this.testService = testService;
    }

    @Override
    public void startTest() throws QuestionLoadException {
        baseTestService.enterUser();
        baseTestService.enterLocale();
        List<Question> questions = questionsDao.getQuestions();
        List<String> answers = testService.answerTheQuestions(questions);
        baseTestService.printResult(TestUtil.evaluate(answers, questions), testService.getPrintResult(answers, questions));
    }
}
