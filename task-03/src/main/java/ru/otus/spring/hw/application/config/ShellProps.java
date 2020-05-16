package ru.otus.spring.hw.application.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "shell")
public class ShellProps {

    private OutProps out;

    public OutProps getOut() {
        return out;
    }

    public void setOut(OutProps out) {
        this.out = out;
    }
}
