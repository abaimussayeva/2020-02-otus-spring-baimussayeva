package ru.otus.spring.hw.application.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import ru.otus.spring.hw.domain.business.IOService;
import ru.otus.spring.hw.domain.business.dao.QuestionsDao;
import ru.otus.spring.hw.domain.business.l10n.L10nService;
import ru.otus.spring.hw.domain.business.login.SignInService;
import ru.otus.spring.hw.domain.business.test.TestService;
import ru.otus.spring.hw.domain.business.test.BaseService;
import ru.otus.spring.hw.domain.errors.QuestionLoadException;
import ru.otus.spring.hw.domain.model.Person;
import ru.otus.spring.hw.domain.model.Question;
import ru.otus.spring.hw.util.TestUtil;

import java.util.List;
import java.util.Optional;

@ShellComponent
@RequiredArgsConstructor
public class AppCommands {

    private final SignInService signInService;

    private final L10nService l10nService;

    private final BaseService baseService;

    private final QuestionsDao questionsDao;

    private final TestService testService;

    private final IOService ioService;

    @ShellMethod(value = "Login command", key = {"l", "login"})
    @ShellMethodAvailability(value = "isLoginAvailable")
    public String login() {
        baseService.enterUser();
        baseService.enterLocale();
        return l10nService.getMessage("thank_you");
    }

    @ShellMethod(value = "Test start command", key = {"start", "run", "test"})
    @ShellMethodAvailability(value = "isTestAvailable")
    public void start() {
        try {
            List<Question> questions = questionsDao.getQuestions();
            List<String> answers = testService.answerTheQuestions(questions);
            baseService.printResult(TestUtil.evaluate(answers, questions),
                    testService.getPrintResult(answers, questions));
        } catch (QuestionLoadException e) {
            ioService.printError(e.getMessage());
        }
    }

    private Availability isTestAvailable() {
        return signInService.getLoggedUser().isEmpty() ?
                Availability.unavailable(l10nService.getMessage("login_first")) :
                Availability.available();
    }

    private Availability isLoginAvailable() {
        Optional<Person> person =  signInService.getLoggedUser();
        return person.isPresent() ?
                Availability.unavailable(l10nService.getMessage("already_signed_in", person.get().getFirstName(), person.get().getLastName())) :
                Availability.available();
    }
}

