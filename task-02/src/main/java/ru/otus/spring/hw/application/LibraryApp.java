package ru.otus.spring.hw.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.otus.spring.hw.application.config.AppProps;
import ru.otus.spring.hw.application.config.ShellProps;

import java.sql.SQLException;

@SpringBootApplication
@EnableConfigurationProperties({AppProps.class, ShellProps.class})
public class LibraryApp {

    public static void main(String[] args) throws SQLException {
        SpringApplication.run(LibraryApp.class, args);
    }
}