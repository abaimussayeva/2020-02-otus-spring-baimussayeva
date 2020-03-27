package ru.otus.spring.hw.application.config;

import org.springframework.stereotype.Component;
import ru.otus.spring.hw.domain.model.Person;

@Component
public class UserProps {
    private Person loggedUser;

    public Person getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(Person loggedUser) {
        this.loggedUser = loggedUser;
    }
}
