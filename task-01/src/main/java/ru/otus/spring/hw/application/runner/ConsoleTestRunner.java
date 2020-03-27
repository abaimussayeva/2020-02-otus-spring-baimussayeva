package ru.otus.spring.hw.application.runner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.otus.spring.hw.domain.application.TestStartService;
import ru.otus.spring.hw.domain.errors.QuestionLoadException;

@Component
public class ConsoleTestRunner implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsoleTestRunner.class);

    private final TestStartService testStartService;

    public ConsoleTestRunner(TestStartService testStartService) {
        this.testStartService = testStartService;
    }

    @Override
    public void run(String... args) {
        LOGGER.info("Starting test service");
        try {
            testStartService.startTest();
        } catch (QuestionLoadException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}
