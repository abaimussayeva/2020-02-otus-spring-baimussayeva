package ru.otus.spring.hw.application.business;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.spring.hw.domain.business.login.SignInService;
import ru.otus.spring.hw.domain.model.Person;

import java.util.Locale;
import java.util.Scanner;

@Service
public class SignInServiceConsole implements SignInService {

    private final MessageSource messageSource;

    public SignInServiceConsole(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public Person signIn(String defaultLocale) {
        Scanner scanner = new Scanner(System.in);
        Locale locale = new Locale(defaultLocale);
        System.out.println(messageSource.getMessage("welcome",new Object[]{}, locale));
        System.out.println(messageSource.getMessage("enter_locale", new Object[]{}, locale));
        String useLocale = scanner.next();
        if ("ru".equalsIgnoreCase(useLocale) || "en".equalsIgnoreCase(useLocale)) {
            locale = new Locale(useLocale);
        }
        System.out.println(messageSource.getMessage("enter_name", new Object[]{}, locale));
        String name = scanner.next();
        System.out.println(messageSource.getMessage("enter_lastname", new Object[]{}, locale));
        String lastName = scanner.next();
        return new Person(name, lastName, locale);
    }
}
