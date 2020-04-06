package ru.otus.spring.hw.domain.errors;

public class DBOperationException extends Exception {

    public DBOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}
