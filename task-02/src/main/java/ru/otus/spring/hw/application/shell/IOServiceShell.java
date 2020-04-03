package ru.otus.spring.hw.application.shell;

import org.jline.reader.LineReader;
import org.jline.terminal.Terminal;
import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import ru.otus.spring.hw.application.config.OutProps;
import ru.otus.spring.hw.application.config.ShellProps;
import ru.otus.spring.hw.domain.business.IOService;
import ru.otus.spring.hw.util.PromptColor;

@Service
public class IOServiceShell implements IOService {

    private final Terminal terminal;

    private final LineReader lineReader;

    private final OutProps outProps;

    public IOServiceShell(@Lazy Terminal terminal, @Lazy LineReader lineReader, ShellProps shellProps) {
        this.terminal = terminal;
        this.lineReader = lineReader;
        this.outProps = shellProps.getOut();
    }

    @Override
    public void out(String message) {
        print(message, null);
    }

    @Override
    public String readString(String prompt) {
        return lineReader.readLine(prompt + ": ");
    }

    public void printSuccess(String message) {
        print(message, PromptColor.valueOf(outProps.getSuccess()));
    }

    public void printWarning(String message) {
        print(message, PromptColor.valueOf(outProps.getWarning()));
    }

    public void printError(String message) {
        print(message, PromptColor.valueOf(outProps.getError()));
    }

    public void printInfo(String message) {
        print(message, PromptColor.valueOf(outProps.getInfo()));
    }

    @Override
    public void printKeyValue(String key, String value) {
        terminal.writer().print(key + ": ");
        terminal.writer().println(getColored("  " + value, PromptColor.valueOf(outProps.getSuccess())));
        terminal.flush();
    }

    public void print(String message, PromptColor color) {
        String toPrint = message;
        if (color != null) {
            toPrint = getColored(message, color);
        }
        terminal.writer().println(toPrint);
        terminal.flush();
    }

    public String getColored(String message, PromptColor color) {
        return (new AttributedStringBuilder()).append(message,
                AttributedStyle.DEFAULT.foreground(color.toJlineAttributedStyle())).toAnsi();
    }
}
