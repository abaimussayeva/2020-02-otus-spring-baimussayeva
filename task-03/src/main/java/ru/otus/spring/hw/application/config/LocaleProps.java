package ru.otus.spring.hw.application.config;

import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Map;

@Component
public class LocaleProps {

    private final String defaultLocale;
    private Locale locale;
    private Map<String, String> locales;

    public LocaleProps(AppProps appProps) {
        this.defaultLocale = appProps.getLocales().values().stream().findFirst().orElseThrow();
        this.locale = Locale.forLanguageTag(defaultLocale);
        this.locales = appProps.getLocales();
    }

    public String getDefaultLocale() {
        return defaultLocale;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public Map<String, String> getLocales() {
        return locales;
    }
}
