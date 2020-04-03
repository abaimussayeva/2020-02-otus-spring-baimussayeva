package ru.otus.spring.hw.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.otus.spring.hw.application.config.ShellProps;

@SpringBootApplication
@EnableConfigurationProperties({ShellProps.class})
public class LibraryApp {

    public static void main(String[] args) {
        SpringApplication.run(LibraryApp.class, args);
    }
}
