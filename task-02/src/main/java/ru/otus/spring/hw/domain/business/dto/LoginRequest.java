package ru.otus.spring.hw.domain.business.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String login;
    private String password;
}
