package ru.otus.spring.hw.application.datasource;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.otus.spring.hw.application.config.AppProps;
import ru.otus.spring.hw.application.config.LocaleProps;
import ru.otus.spring.hw.domain.errors.QuestionLoadException;
import ru.otus.spring.hw.domain.model.Question;
import ru.otus.spring.hw.domain.model.Variant;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class QuestionsDaoUnitTest {

    static Stream<Arguments> answersAndQuestions() {
        return Stream.of(
                arguments("en", "en-US", "test_en_US.csv", "When was the first Java version released?", "1996", "1992", "1990", "1998"),
                arguments("ru", "ru-RU", "test_ru_RU.csv", "Когда была выпущена первая версия Java?", "1996", "1992", "1990", "1998")
        );
    }

    @ParameterizedTest
    @MethodSource("answersAndQuestions")
    void getQuestionsTest(String locale, String localeCode, String fileName,
                          String question, String a, String b, String c, String d) throws QuestionLoadException {
        AppProps props = new AppProps();
        props.setLocales(Collections.singletonMap(locale, localeCode));
        props.setQuestionFiles(Collections.singletonMap(localeCode.replace("-", "_"), fileName));
        LocaleProps localeProps = new LocaleProps(props);
        QuestionsDaoCsv questionsDaoCsv = new QuestionsDaoCsv(localeProps);
        List<Question> questions = questionsDaoCsv.getQuestions();
        assertEquals(questions.size(), 4);
        assertEquals(questions.get(0).getAnswer(), "A");
        assertEquals(questions.get(0).getQuestion(), question);
        assertEquals(questions.get(0).getVariant(Variant.A), a);
        assertEquals(questions.get(0).getVariant(Variant.B), b);
        assertEquals(questions.get(0).getVariant(Variant.C), c);
        assertEquals(questions.get(0).getVariant(Variant.D), d);
    }

    @Test
    void getQuestionsTestFail() {
        AppProps props = new AppProps();
        props.setLocales(Collections.singletonMap("en", "en_US"));
        props.setQuestionFiles(Collections.singletonMap("en_US", "test-file.csv"));
        LocaleProps localeProps = new LocaleProps(props);
        QuestionsDaoCsv questionsDaoCsv = new QuestionsDaoCsv(localeProps);
        assertThrows(QuestionLoadException.class, () -> questionsDaoCsv.getQuestions(), "Error occurred while questions load");
    }

    @Test
    void getQuestionsTestIOFail() {
        AppProps props = new AppProps();
        props.setLocales(Collections.singletonMap("en", "en_US"));
        props.setQuestionFiles(Collections.singletonMap("en_US", "test-no-resource.csv"));
        LocaleProps localeProps = new LocaleProps(props);
        QuestionsDaoCsv questionsDaoCsv = new QuestionsDaoCsv(localeProps);
        assertThrows(QuestionLoadException.class, () -> questionsDaoCsv.getQuestions(), "Error occurred while questions load");
    }
}