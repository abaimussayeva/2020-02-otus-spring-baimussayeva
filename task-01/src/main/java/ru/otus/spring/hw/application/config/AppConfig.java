package ru.otus.spring.hw.application.config;

import org.jline.reader.LineReader;
import org.jline.terminal.Terminal;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import ru.otus.spring.hw.application.console.ConsoleContext;
import ru.otus.spring.hw.application.console.IOServiceConsole;
import ru.otus.spring.hw.application.shell.IOServiceShell;
import ru.otus.spring.hw.application.shell.props.ShellProps;
import ru.otus.spring.hw.domain.business.IOService;

@Configuration
public class AppConfig {

    @Bean
    @ConditionalOnProperty(value = "spring.shell.interactive.enabled", havingValue = "true")
    public IOService ioServiceShell(@Lazy Terminal terminal, @Lazy LineReader lineReader, ShellProps shellProps) {
        return new IOServiceShell(terminal, lineReader, shellProps);
    }

    @Bean
    @ConditionalOnProperty(value = "spring.shell.interactive.enabled", havingValue = "false")
    public IOService ioServiceConsole(ConsoleContext consoleContext) {
        return new IOServiceConsole(consoleContext);
    }
}