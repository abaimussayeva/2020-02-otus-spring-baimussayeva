package ru.otus.spring.hw.application.business;

import org.springframework.stereotype.Service;
import ru.otus.spring.hw.application.config.ConsoleContext;
import ru.otus.spring.hw.domain.business.IOService;

import java.io.PrintStream;
import java.util.Scanner;

@Service
public class ConsoleIOService implements IOService {

    private final PrintStream out;
    private final Scanner scanner;

    public ConsoleIOService(ConsoleContext ctx) {
        this.out = ctx.getOut();
        this.scanner = new Scanner(ctx.getIn());
    }

    @Override
    public void out(String message) {
        out.println(message);
    }

    @Override
    public String readString() {
        return scanner.nextLine();
    }
}
