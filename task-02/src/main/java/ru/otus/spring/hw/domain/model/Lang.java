package ru.otus.spring.hw.domain.model;

public class Lang {
    private final long langId;
    private final String name;

    public Lang(long langId, String name) {
        this.langId = langId;
        this.name = name;
    }

    public long getLangId() {
        return langId;
    }

    public String getName() {
        return name;
    }
}
