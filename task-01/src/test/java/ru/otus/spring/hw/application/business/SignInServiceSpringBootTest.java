package ru.otus.spring.hw.application.business;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.spring.hw.application.config.UserProps;
import ru.otus.spring.hw.domain.business.login.SignInService;
import ru.otus.spring.hw.domain.model.Person;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class SignInServiceSpringBootTest {

    @Configuration
    static class TestConfiguration {
        @Bean
        public UserProps userProps() {
            return new UserProps();
        }
        @Bean
        public SignInService signInService(UserProps userProps) {
            return new SignInServiceConsole(userProps);
        }
    }

    @Autowired
    private SignInService signInService;

    @Test
    void signInTest() {
        signInService.signIn("FirstName", "LastName");
        Person person = signInService.getLoggedUser().orElseThrow();
        assertEquals("FirstName", person.getFirstName());
        assertEquals("LastName", person.getLastName());
    }
}
