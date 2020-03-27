package ru.otus.spring.hw.application.l10n;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.spring.hw.domain.application.TestStartService;
import ru.otus.spring.hw.domain.business.IOService;
import ru.otus.spring.hw.domain.business.dao.QuestionsDao;
import ru.otus.spring.hw.domain.business.l10n.L10nService;
import ru.otus.spring.hw.domain.business.login.SignInService;
import ru.otus.spring.hw.domain.errors.QuestionLoadException;
import ru.otus.spring.hw.domain.model.Question;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@SpringBootTest
public class L10nServiceTest {

    @Autowired
    private L10nService l10nService;

    @MockBean
    private TestStartService testStartService;

    @MockBean
    private IOService ioService;

    @MockBean
    private SignInService signInService;

    @Autowired
    private QuestionsDao questionsDao;

    @ParameterizedTest
    @MethodSource("localesAndQuestions")
    void chooseLocaleTest(String locale, String expectedQuestion) throws QuestionLoadException {
        l10nService.chooseLocale(locale);
        List<Question> questions = questionsDao.getQuestions();
        assertEquals(4, questions.size());
        assertEquals(expectedQuestion, questions.get(0).getQuestion());
    }

    static Stream<Arguments> localesAndQuestions() {
        return Stream.of(
                arguments("fr", "When was the first Java version released?"),
                arguments("kk", "Бірінші Java нұсқасы қашан шықты?"),
                arguments("unknown", "When was the first Java version released?"),
                arguments("ru", "Когда была выпущена первая версия Java?")
        );
    }
}
