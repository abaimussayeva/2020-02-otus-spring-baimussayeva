package ru.otus.spring.hw.application.datasource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.spring.hw.domain.application.TestStartService;
import ru.otus.spring.hw.domain.business.dao.QuestionsDao;
import ru.otus.spring.hw.domain.errors.QuestionLoadException;
import ru.otus.spring.hw.domain.model.Question;
import ru.otus.spring.hw.domain.model.Variant;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class QuestionsDaoSpringBootTest {

    @MockBean
    private TestStartService testStartService;

    @Autowired
    private QuestionsDao questionsDao;

    @Test
    void loadQuestionsTest() throws QuestionLoadException {
        List<Question> questions = questionsDao.getQuestions();
        assertEquals(4, questions.size());
        assertEquals(questions.get(0).getAnswer(), "A");
        assertEquals(questions.get(0).getQuestion(), "When was the first Java version released?");
        assertEquals(questions.get(0).getVariant(Variant.A), "1996");
        assertEquals(questions.get(0).getVariant(Variant.B), "1992");
        assertEquals(questions.get(0).getVariant(Variant.C), "1990");
        assertEquals(questions.get(0).getVariant(Variant.D), "1998");
    }
}