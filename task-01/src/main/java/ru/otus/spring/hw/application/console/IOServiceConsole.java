package ru.otus.spring.hw.application.console;

import ru.otus.spring.hw.domain.business.IOService;
import ru.otus.spring.hw.util.PromptColor;

import java.io.PrintStream;
import java.util.Scanner;

public class IOServiceConsole implements IOService {

    private final PrintStream out;
    private final Scanner scanner;

    public IOServiceConsole(ConsoleContext ctx) {
        this.out = ctx.getOut();
        this.scanner = new Scanner(ctx.getIn());
    }

    @Override
    public void out(String message) {
        out.println(message);
    }

    @Override
    public String readString(String prompt) {
        out.print(String.format("%s: ", prompt));
        return scanner.nextLine();
    }

    @Override
    public void printWarning(String message) {
        out.println(PromptColor.YELLOW.getConsoleValue() + message);
    }

    @Override
    public void printError(String message) {
        out.println(PromptColor.RED.getConsoleValue() + message);
    }

    @Override
    public void printSuccess(String message) {
        out.println(PromptColor.GREEN.getConsoleValue() + message);
    }

    @Override
    public void printInfo(String message) {
        out.println(PromptColor.CYAN.getConsoleValue() + message);
    }
}
