package ru.otus.spring.hw.domain.business.login;

import ru.otus.spring.hw.domain.model.Person;

import java.util.Optional;

public interface SignInService {
    void signIn(String firstName, String lastName);
    Optional<Person> getLoggedUser();
}
