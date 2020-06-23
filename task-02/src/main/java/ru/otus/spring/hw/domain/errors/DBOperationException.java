package ru.otus.spring.hw.domain.errors;

public class DBOperationException extends RuntimeException {

    public DBOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}
