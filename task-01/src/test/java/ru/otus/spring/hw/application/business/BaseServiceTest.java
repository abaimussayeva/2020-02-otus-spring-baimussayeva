package ru.otus.spring.hw.application.business;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.spring.hw.application.config.LocaleProps;
import ru.otus.spring.hw.domain.application.TestStartService;
import ru.otus.spring.hw.domain.business.IOService;
import ru.otus.spring.hw.domain.business.login.SignInService;
import ru.otus.spring.hw.domain.business.test.BaseService;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class BaseServiceTest {

    @Autowired
    private BaseService baseService;

    @Autowired
    private LocaleProps localeProps;

    @Autowired
    private SignInService signInService;

    @MockBean
    private IOService ioService;

    @MockBean
    private TestStartService testStartService;

    @Test
    void enterLocaleTest() {
        when(ioService.selectFromList(any(), any(), any(), any()))
                .thenAnswer(invocationOnMock -> "kk");
        baseService.enterLocale();
        assertEquals(Locale.forLanguageTag("kk-KZ"), localeProps.getLocale());
    }

    @Test
    void enterUserTest() {
        when(ioService.readString(any()))
                .thenAnswer(invocationOnMock -> "Aigul")
                .thenAnswer(invocationOnMock -> "Baimussayeva");
        baseService.enterUser();
        assertEquals(signInService.getLoggedUser().orElseThrow().getFirstName(), "Aigul");
        assertEquals(signInService.getLoggedUser().orElseThrow().getLastName(), "Baimussayeva");
    }
}
