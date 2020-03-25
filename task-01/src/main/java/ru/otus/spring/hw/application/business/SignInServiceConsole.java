package ru.otus.spring.hw.application.business;

import org.springframework.stereotype.Service;
import ru.otus.spring.hw.domain.business.IOService;
import ru.otus.spring.hw.domain.business.l10n.L10nService;
import ru.otus.spring.hw.domain.business.login.SignInService;
import ru.otus.spring.hw.domain.model.Person;

@Service
public class SignInServiceConsole implements SignInService {

    private final L10nService l10nService;
    private final IOService ioService;

    public SignInServiceConsole(L10nService l10nService, IOService ioService) {
        this.l10nService = l10nService;
        this.ioService = ioService;
    }

    @Override
    public Person signIn() {
        ioService.out(l10nService.getMessage("enter_name"));
        String name = ioService.readString();
        ioService.out(l10nService.getMessage("enter_lastname"));
        String lastName = ioService.readString();
        return new Person(name, lastName);
    }
}
