package ru.otus.spring.hw.application.shell.props;

import org.springframework.boot.context.properties.ConfigurationProperties;
import ru.otus.spring.hw.application.config.OutProps;

@ConfigurationProperties(prefix = "shell")
public class ShellProps {

    private OutProps out;

    public OutProps getOut() {
        return out;
    }

    public void setOut(OutProps out) {
        this.out = out;
    }
}
