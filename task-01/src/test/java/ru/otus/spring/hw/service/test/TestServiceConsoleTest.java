package ru.otus.spring.hw.service.test;


import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.otus.spring.hw.domain.Question;
import ru.otus.spring.hw.domain.Variant;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class TestServiceConsoleTest {

    private TestServiceConsole testServiceConsole = new TestServiceConsole();

    @ParameterizedTest
    @MethodSource("answersAndQuestions")
    void testEvaluate(List<String> answers, List<Question> questions, int expected) {
        assertEquals(testServiceConsole.evaluate(answers, questions), expected);
    }

    static Stream<Arguments> answersAndQuestions() {
        List<Question> questions = Arrays.asList(
                new Question("bla 1", Map.of(Variant.A, "A", Variant.B, "B", Variant.C, "C"), "A"),
                new Question("bla 2", Map.of(Variant.A, "A", Variant.B, "B", Variant.C, "C"), "B"),
                new Question("bla 2", Map.of(Variant.A, "A", Variant.B, "B", Variant.C, "C"), "C"));
        return Stream.of(
                arguments(Arrays.asList("A", "B", "C"), questions, 3),
                arguments(Arrays.asList("A", "B", "B"), questions, 2),
                arguments(Arrays.asList("A", "C", "B"), questions, 1),
                arguments(Arrays.asList("C", "C", "B"), questions, 0)
        );
    }

    @ParameterizedTest
    @MethodSource("answersAndQuestionsDiff")
    void testEvaluateDiffSize(List<String> answers, List<Question> questions) {
        assertThrows(IllegalArgumentException.class, () -> testServiceConsole.evaluate(answers, questions));
    }

    static Stream<Arguments> answersAndQuestionsDiff() {
        List<Question> questions = Arrays.asList(
                new Question("bla 1", Map.of(Variant.A, "A", Variant.B, "B", Variant.C, "C"), "A"),
                new Question("bla 2", Map.of(Variant.A, "A", Variant.B, "B", Variant.C, "C"), "B"),
                new Question("bla 2", Map.of(Variant.A, "A", Variant.B, "B", Variant.C, "C"), "C"));
        return Stream.of(
                arguments(Arrays.asList("A", "B", "C", "D"), questions),
                arguments(Collections.emptyList(), questions),
                arguments(null, null),
                arguments(Arrays.asList("A", "B", "C", "D"), Collections.emptyList()),
                arguments(null, Collections.emptyList()),
                arguments(Collections.emptyList(), null)
        );
    }
}