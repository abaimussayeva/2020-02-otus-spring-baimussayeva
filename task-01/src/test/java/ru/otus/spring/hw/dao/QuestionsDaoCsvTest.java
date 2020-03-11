package ru.otus.spring.hw.dao;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import ru.otus.spring.hw.application.datasource.QuestionsDaoCsv;
import ru.otus.spring.hw.domain.model.Question;
import ru.otus.spring.hw.domain.model.Variant;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class QuestionsDaoCsvTest {

    static ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();

    @BeforeAll
    static void init() {
        ms.setBasename("/i18n/bundle");
        ms.setDefaultEncoding("UTF-8");
    }

    @Test
    void getQuestionsTest() throws IOException {
        QuestionsDaoCsv questionsDaoCsv = new QuestionsDaoCsv("test.csv", ms);
        List<Question> questions = questionsDaoCsv.getQuestions(new Locale("en"));
        assertEquals(questions.size(), 4);
        assertEquals(questions.get(0).getAnswer(), "A");
        assertEquals(questions.get(0).getQuestion(), "When was the first Java version released?");
        assertEquals(questions.get(0).getVariant(Variant.A), "1996");
        assertEquals(questions.get(0).getVariant(Variant.B), "1992");
        assertEquals(questions.get(0).getVariant(Variant.C), "1990");
        assertEquals(questions.get(0).getVariant(Variant.D), "1998");
        questions = questionsDaoCsv.getQuestions(new Locale("ru"));
        assertEquals(questions.size(), 4);
        assertEquals(questions.get(2).getAnswer(), "B");
        assertEquals(questions.get(2).getQuestion(), "Каков будет результат выполнения следующего кода - System.out.print(1+5 + \" Result = \" + 5 + 6)?");
        assertEquals(questions.get(2).getVariant(Variant.A), "6 Result = 11");
        assertEquals(questions.get(2).getVariant(Variant.B), "6 Result = 56");
        assertEquals(questions.get(2).getVariant(Variant.C), "15 Result = 56");
        assertEquals(questions.get(2).getVariant(Variant.D), "Code won't compile");
    }

    @Test
    void getQuestionsTestFail() {
        QuestionsDaoCsv questionsDaoCsv = new QuestionsDaoCsv("test-fail.csv", ms);
        assertThrows(IllegalArgumentException.class, () -> questionsDaoCsv.getQuestions(new Locale("ru")), "Index for header 'Answer' is 5 but CSVRecord only has 5 values!");
    }

    @Test
    void getQuestionsTestIOFail() {
        QuestionsDaoCsv questionsDaoCsv = new QuestionsDaoCsv("test-no-resource.csv", ms);
        assertThrows(NullPointerException.class, () -> questionsDaoCsv.getQuestions(new Locale("ru")));
    }
}