package ru.otus.spring.hw.domain.business.l10n;

public interface L10nService {
    void chooseLocale();
    String getMessage(String key, Object... args);
}
