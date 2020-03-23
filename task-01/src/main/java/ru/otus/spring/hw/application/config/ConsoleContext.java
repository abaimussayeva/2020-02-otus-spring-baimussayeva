package ru.otus.spring.hw.application.config;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.PrintStream;

@Component
@NoArgsConstructor
@AllArgsConstructor
public class ConsoleContext {
    private PrintStream out = System.out;
    private InputStream in = System.in;

    public PrintStream getOut() {
        return out;
    }

    public InputStream getIn() {
        return in;
    }
}
