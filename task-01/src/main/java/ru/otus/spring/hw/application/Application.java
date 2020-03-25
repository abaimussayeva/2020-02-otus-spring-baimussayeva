package ru.otus.spring.hw.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.otus.spring.hw.application.config.AppProps;
import ru.otus.spring.hw.application.shell.props.ShellProps;

@SpringBootApplication
@EnableConfigurationProperties({AppProps.class, ShellProps.class})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
