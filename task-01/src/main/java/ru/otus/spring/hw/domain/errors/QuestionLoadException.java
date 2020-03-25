package ru.otus.spring.hw.domain.errors;

public class QuestionLoadException extends Exception {

    public QuestionLoadException(String message, Throwable cause) {
        super(message, cause);
    }
}
