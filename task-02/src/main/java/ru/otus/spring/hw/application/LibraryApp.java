package ru.otus.spring.hw.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.otus.spring.hw.application.config.AppProps;
import ru.otus.spring.hw.application.config.ShellProps;

@SpringBootApplication
@EnableConfigurationProperties({AppProps.class, ShellProps.class})
@EntityScan(basePackages = "ru.otus.spring.hw.domain.model")
public class LibraryApp {

    public static void main(String[] args) {
        SpringApplication.run(LibraryApp.class, args);
    }
}
