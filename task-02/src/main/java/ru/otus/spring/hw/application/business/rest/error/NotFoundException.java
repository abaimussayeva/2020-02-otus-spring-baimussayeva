package ru.otus.spring.hw.application.business.rest.error;

public class NotFoundException extends RuntimeException {

    public NotFoundException() {
    }

    public NotFoundException( String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue));
    }
}
