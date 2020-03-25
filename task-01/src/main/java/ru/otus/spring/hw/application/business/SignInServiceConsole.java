package ru.otus.spring.hw.application.business;

import org.springframework.stereotype.Service;
import ru.otus.spring.hw.application.config.UserProps;
import ru.otus.spring.hw.domain.business.login.SignInService;
import ru.otus.spring.hw.domain.model.Person;

import java.util.Optional;

@Service
public class SignInServiceConsole implements SignInService {

    private final UserProps userProps;

    public SignInServiceConsole(UserProps userProps) {
        this.userProps = userProps;
    }

    @Override
    public void signIn(String firstName, String lastName) {
        userProps.setLoggedUser(new Person(firstName, lastName));
    }

    @Override
    public Optional<Person> getLoggedUser() {
        return userProps.getLoggedUser() != null ? Optional.of(userProps.getLoggedUser()) : Optional.empty();
    }
}
