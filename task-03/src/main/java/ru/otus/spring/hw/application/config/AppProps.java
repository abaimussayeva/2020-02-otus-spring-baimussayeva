package ru.otus.spring.hw.application.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties(prefix = "application")
public class AppProps {

    private Map<String, String> locales;

    public Map<String, String> getLocales() {
        return locales;
    }

    public void setLocales(Map<String, String> locales) {
        this.locales = locales;
    }
}
