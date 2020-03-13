package ru.otus.spring.hw.domain.model;

import java.util.Locale;

public class Person {

    private final String firstName;
    private final String lastName;
    private final Locale locale;

    public Person(String firstName, String lastName, Locale locale) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.locale = locale;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Locale getLocale() {
        return locale;
    }
}
