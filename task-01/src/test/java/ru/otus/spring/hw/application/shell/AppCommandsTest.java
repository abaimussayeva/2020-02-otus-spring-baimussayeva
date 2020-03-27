package ru.otus.spring.hw.application.shell;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.shell.CommandNotCurrentlyAvailable;
import org.springframework.shell.Shell;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.spring.hw.domain.application.TestStartService;
import ru.otus.spring.hw.domain.business.IOService;
import ru.otus.spring.hw.domain.business.dao.QuestionsDao;
import ru.otus.spring.hw.domain.business.login.SignInService;
import ru.otus.spring.hw.domain.business.test.BaseService;
import ru.otus.spring.hw.domain.business.test.TestService;
import ru.otus.spring.hw.domain.errors.QuestionLoadException;
import ru.otus.spring.hw.domain.model.Person;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@DisplayName("Тест команд shell ")
@SpringBootTest
class AppCommandsTest {

    @Autowired
    private Shell shell;

    @MockBean
    private BaseService baseService;

    @MockBean
    TestStartService testStartService;

    @MockBean
    SignInService signInService;

    @MockBean
    QuestionsDao questionsDao;

    @MockBean
    TestService testService;

    @MockBean
    private IOService ioService;

    private static final String COMMAND_LOGIN = "login";
    private static final String COMMAND_LOGIN_SHORT = "l";
    private static final String COMMAND_START = "start";

    @DisplayName(" должен вызвать метод логина пользователя и ввода локали")
    @Test
    void shouldLoginAndEnterLocale() {
        when(ioService.readString(anyString()))
                .thenAnswer(mock -> "Aigul")
                .thenAnswer(mock -> "Baimussayeva")
                .thenAnswer(mock -> "en");
        String res = (String) shell.evaluate(() -> COMMAND_LOGIN);
        assertThat(res).isEqualTo("Thank you");
        verify(baseService, times(1)).enterUser();
        verify(baseService, times(1)).enterLocale();

    }

    @DisplayName(" должен ответить, что логин уже прошел")
    @Test
    void shouldSayThatUserIsSignedIn() {
        Person person = new Person("Aigul", "Baimussayeva");
        when(signInService.getLoggedUser()).thenAnswer(mock -> Optional.of(person));
        CommandNotCurrentlyAvailable res = (CommandNotCurrentlyAvailable) shell.evaluate(() -> COMMAND_LOGIN_SHORT);
        assertThat(res.getMessage()).endsWith("User Aigul Baimussayeva is already signed in");
        verify(baseService, times(0)).enterUser();
        verify(baseService, times(0)).enterLocale();
    }

    @DisplayName(" должен возвращать CommandNotCurrentlyAvailable если при попытке выполнения команды start пользователь не выполнил вход")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Test
    void shouldReturnCommandNotCurrentlyAvailableObjectWhenUserDoesNotLogin() {
        Object res =  shell.evaluate(() -> COMMAND_START);
        assertThat(res).isInstanceOf(CommandNotCurrentlyAvailable.class);
    }

    @DisplayName(" должен запустить тест")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    void shouldPerformTest() throws QuestionLoadException {
        Person person = new Person("Aigul", "Baimussayeva");
        when(signInService.getLoggedUser()).thenAnswer(mock -> Optional.of(person));
        shell.evaluate(() -> COMMAND_START);
        verify(questionsDao, times(1)).getQuestions();
        verify(testService, times(1)).answerTheQuestions(anyList());
        verify(testService, times(1)).getPrintResult(anyList(), anyList());
        verify(baseService, times(1)).printResult(anyInt(), any());
    }
}