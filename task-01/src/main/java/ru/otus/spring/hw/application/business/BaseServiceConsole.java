package ru.otus.spring.hw.application.business;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import ru.otus.spring.hw.domain.business.IOService;
import ru.otus.spring.hw.domain.business.l10n.L10nService;
import ru.otus.spring.hw.domain.business.login.SignInService;
import ru.otus.spring.hw.domain.business.test.BaseService;
import ru.otus.spring.hw.domain.model.Person;

@Controller
public class BaseServiceConsole implements BaseService {

    private final SignInService signInService;
    private final L10nService l10nService;
    private final IOService ioService;

    public BaseServiceConsole(SignInService signInService,
                              L10nService l10nService, IOService ioService) {
        this.signInService = signInService;
        this.l10nService = l10nService;
        this.ioService = ioService;
    }

    @Override
    public void enterLocale() {
        String locale = ioService.selectFromList(l10nService.getMessage("locale"), l10nService.getMessage("choose"),
                l10nService.availableLocales(), null);
        l10nService.chooseLocale(locale);
    }

    @Override
    public void enterUser() {
        ioService.out(l10nService.getMessage("welcome"));
        String firstName;
        do {
            firstName = ioService.readString(l10nService.getMessage("enter_name"));
            if (!StringUtils.hasText(firstName)) {
                ioService.printWarning(l10nService.getMessage("name_empty_error"));
            }
        } while (firstName == null);
        String lastName;
        do {
            lastName = ioService.readString(l10nService.getMessage("enter_lastname"));
            if (!StringUtils.hasText(firstName)) {
                ioService.printWarning(l10nService.getMessage("last_name_empty_error"));
            }
        } while (lastName == null);
        signInService.signIn(firstName, lastName);
    }

    @Override
    public void printResult(int score, String result) {
        Person person = signInService.getLoggedUser().orElseThrow();
        ioService.printInfo("====================================");
        ioService.printInfo(l10nService.getMessage("test_finished", person.getFirstName(), person.getLastName()));
        ioService.printInfo(l10nService.getMessage("score", score));
        ioService.printInfo(l10nService.getMessage("analysis"));
        ioService.out(result);
    }
}
