package ru.otus.spring.hw.util;

public enum  EditType {
    name(1),
    authors(2),
    genre(3),
    lang(4);

    private int order;

    EditType(int order) {
        this.order = order;
    }

    public int getOrder() {
        return order;
    }
}
