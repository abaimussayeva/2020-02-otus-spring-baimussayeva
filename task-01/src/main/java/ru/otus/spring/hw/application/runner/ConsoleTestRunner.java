package ru.otus.spring.hw.application.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.otus.spring.hw.domain.application.TestStartService;

@Component
public class ConsoleTestRunner implements CommandLineRunner {

    private final TestStartService testStartService;

    public ConsoleTestRunner(TestStartService testStartService) {
        this.testStartService = testStartService;
    }

    @Override
    public void run(String... args) throws Exception {
        testStartService.startTest();
    }
}
