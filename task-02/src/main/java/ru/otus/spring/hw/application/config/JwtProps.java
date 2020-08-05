package ru.otus.spring.hw.application.config;

import lombok.Data;

@Data
public class JwtProps {
    private String secret;
    private Long expirationInMs;
}
