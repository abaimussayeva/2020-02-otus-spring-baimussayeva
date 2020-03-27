package ru.otus.spring.hw.application.business;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.spring.hw.domain.application.TestStartService;
import ru.otus.spring.hw.domain.business.IOService;
import ru.otus.spring.hw.domain.business.dao.QuestionsDao;
import ru.otus.spring.hw.domain.business.test.TestService;
import ru.otus.spring.hw.domain.errors.QuestionLoadException;
import ru.otus.spring.hw.domain.model.Question;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertLinesMatch;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestServiceSpringBootTest {
    @MockBean
    private IOService ioService;

    @MockBean
    private TestStartService testStartService;

    @Autowired
    private TestService testService;

    @Autowired
    private QuestionsDao questionsDao;

    @Test
    void answerTest() throws QuestionLoadException {
        when(ioService.selectFromList(any(), any(), any(), any()))
                .thenAnswer(invocationOnMock -> "A")
                .thenAnswer(invocationOnMock -> "B")
                .thenAnswer(invocationOnMock -> "C")
                .thenAnswer(invocationOnMock -> "D");
        List<Question> questions = questionsDao.getQuestions();
        List<String> answers = testService.answerTheQuestions(questions);
        assertLinesMatch(Arrays.asList("A", "B", "C", "D"), answers);
    }
}
