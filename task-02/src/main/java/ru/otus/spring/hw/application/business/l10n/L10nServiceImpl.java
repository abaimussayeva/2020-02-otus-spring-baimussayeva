package ru.otus.spring.hw.application.business.l10n;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.spring.hw.application.config.LocaleProps;
import ru.otus.spring.hw.domain.business.l10n.L10nService;

import java.util.Locale;
import java.util.Map;

@Service
public class L10nServiceImpl implements L10nService {

    private final LocaleProps localeProps;
    private final MessageSource messageSource;

    public L10nServiceImpl(LocaleProps localeProps, MessageSource messageSource) {
        this.localeProps = localeProps;
        this.messageSource = messageSource;
    }

    @Override
    public Map<String, String> availableLocales() {
        return localeProps.getLocales();
    }

    @Override
    public void chooseLocale(String locale) {
        String localeTag = localeProps.getLocales().getOrDefault(locale, localeProps.getDefaultLocale());
        localeProps.setLocale(Locale.forLanguageTag(localeTag));
    }

    @Override
    public String getMessage(String key, Object... args) {
        return messageSource.getMessage(key, args, localeProps.getLocale());
    }
}
