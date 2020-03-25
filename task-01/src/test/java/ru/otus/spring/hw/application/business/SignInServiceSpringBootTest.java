package ru.otus.spring.hw.application.business;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.spring.hw.domain.application.TestStartService;
import ru.otus.spring.hw.domain.business.IOService;
import ru.otus.spring.hw.domain.business.l10n.L10nService;
import ru.otus.spring.hw.domain.business.login.SignInService;
import ru.otus.spring.hw.domain.model.Person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SignInServiceSpringBootTest {

    @MockBean
    private IOService ioService;

    @MockBean
    private TestStartService testStartService;

    @Autowired
    private SignInService signInService;

    @Test
    void signInTest() {
        when(ioService.readString()).thenReturn("FirstName").thenReturn("LastName");
        Person person = signInService.signIn();
        assertEquals("FirstName", person.getFirstName());
        assertEquals("LastName", person.getLastName());
    }
}
