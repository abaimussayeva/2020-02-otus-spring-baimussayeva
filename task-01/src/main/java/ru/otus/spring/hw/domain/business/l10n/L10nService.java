package ru.otus.spring.hw.domain.business.l10n;

import java.util.Map;

public interface L10nService {
    Map<String, String> availableLocales();
    void chooseLocale(String locale);
    String getMessage(String key, Object... args);
}
