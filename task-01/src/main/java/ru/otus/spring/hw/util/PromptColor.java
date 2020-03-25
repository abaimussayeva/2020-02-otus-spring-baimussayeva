package ru.otus.spring.hw.util;

public enum PromptColor {

    BLACK(0, "\u001B[30m"),
    RED(1, "\u001B[31m"),
    GREEN(2, "\u001B[32m"),
    YELLOW(3, "\u001B[33m"),
    BLUE(4, "\u001B[34m"),
    MAGENTA(5, "\u001B[35m"),
    CYAN(6, "\u001B[36m"),
    WHITE(7, "\u001B[37m"),
    BRIGHT(8, "\u001B[38m");

    private final int value;
    private final String consoleValue;

    PromptColor(int value, String consoleValue) {
        this.value = value;
        this.consoleValue = consoleValue;
    }

    public int toJlineAttributedStyle() {
        return this.value;
    }

    public String getConsoleValue() {
        return consoleValue;
    }
}