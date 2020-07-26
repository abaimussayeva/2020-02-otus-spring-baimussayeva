package ru.otus.spring.hw.application.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@Data
@ConfigurationProperties(prefix = "application")
public class AppProps {
    private Map<String, String> locales;
    private JwtProps jwt;
}
