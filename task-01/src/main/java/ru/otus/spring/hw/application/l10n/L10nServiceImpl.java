package ru.otus.spring.hw.application.l10n;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.spring.hw.application.config.LocaleProps;
import ru.otus.spring.hw.domain.business.IOService;
import ru.otus.spring.hw.domain.business.l10n.L10nService;

import java.util.Locale;

@Service
public class L10nServiceImpl implements L10nService {

    private final LocaleProps localeProps;
    private final MessageSource messageSource;
    private final IOService ioService;

    public L10nServiceImpl(LocaleProps localeProps, MessageSource messageSource, IOService ioService) {
        this.localeProps = localeProps;
        this.messageSource = messageSource;
        this.ioService = ioService;
    }

    @Override
    public void chooseLocale() {
        ioService.out(getMessage("welcome"));
        ioService.out(getMessage("choose_locale"));
        localeProps.getLocales().keySet().forEach(l -> ioService.out("* " + l));
        String locale = localeProps.getLocales().getOrDefault(ioService.readString().toLowerCase(), localeProps.getDefaultLocale());
        localeProps.setLocale(Locale.forLanguageTag(locale));
    }

    @Override
    public String getMessage(String key, Object... args) {
        return messageSource.getMessage(key, args, localeProps.getLocale());
    }
}
