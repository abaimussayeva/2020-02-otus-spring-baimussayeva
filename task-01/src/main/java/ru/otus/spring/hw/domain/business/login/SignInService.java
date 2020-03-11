package ru.otus.spring.hw.domain.business.login;

import ru.otus.spring.hw.domain.model.Person;

public interface SignInService {

    Person signIn(String defaultLocale);

}
