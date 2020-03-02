package ru.otus.spring.hw.dao;

import org.junit.jupiter.api.Test;
import ru.otus.spring.hw.domain.Question;
import ru.otus.spring.hw.domain.Variant;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class QuestionsDaoCsvTest {

    @Test
    void getQuestionsTest() throws IOException {
        QuestionsDaoCsv questionsDaoCsv = new QuestionsDaoCsv("test.csv");
        List<Question> questions = questionsDaoCsv.getQuestions();
        assertEquals(questions.size(), 4);
        assertEquals(questions.get(0).getAnswer(), "A");
        assertEquals(questions.get(0).getQuestion(), "When was the first Java version released?");
        assertEquals(questions.get(0).getVariant(Variant.A), "1996");
        assertEquals(questions.get(0).getVariant(Variant.B), "1992");
        assertEquals(questions.get(0).getVariant(Variant.C), "1990");
        assertEquals(questions.get(0).getVariant(Variant.D), "1998");
    }

    @Test
    void getQuestionsTestFail() {
        QuestionsDaoCsv questionsDaoCsv = new QuestionsDaoCsv("test-fail.csv");
        assertThrows(IllegalArgumentException.class, questionsDaoCsv::getQuestions, "Index for header 'Answer' is 5 but CSVRecord only has 5 values!");
    }

    @Test
    void getQuestionsTestIOFail() {
        QuestionsDaoCsv questionsDaoCsv = new QuestionsDaoCsv("test-no-resource.csv");
        assertThrows(NullPointerException.class, questionsDaoCsv::getQuestions);
    }
}