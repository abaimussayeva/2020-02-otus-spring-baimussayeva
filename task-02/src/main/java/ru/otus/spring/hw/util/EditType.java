package ru.otus.spring.hw.util;

import ru.otus.spring.hw.domain.model.Messages;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public enum  EditType {
    name(1, Messages.BOOK_NAME),
    authors(2, Messages.AUTHOR),
    genre(3, Messages.GENRE),
    lang(4, Messages.LANG);

    private int order;
    private String message;

    EditType(int order, String message) {
        this.order = order;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public static EditType getTypeByOrder(String order) {
        for (EditType type: values()) {
            if (Integer.valueOf(order).equals(type.getOrder()))
                return type;
        }
        throw new NoSuchElementException();
    }

    public static Map<String, String> getEditTypeMap() {
        Map<String, String> editTypes = new HashMap<>();
        for (EditType type : EditType.values()) {
            editTypes.put(String.valueOf(type.getOrder()), type.getMessage());
        }
        return editTypes;
    }

    public int getOrder() {
        return order;
    }
}
