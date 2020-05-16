package ru.otus.spring.hw.application.shell;

import org.springframework.stereotype.Component;
import ru.otus.spring.hw.domain.business.l10n.L10nService;
import ru.otus.spring.hw.util.EditType;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@Component
public class EditTypeUtil {

    private final L10nService l10nService;

    public EditTypeUtil(L10nService l10nService) {
        this.l10nService = l10nService;
    }

    public EditType getTypeByOrder(String order) {
        for (EditType type: EditType.values()) {
            if (Integer.valueOf(order).equals(type.getOrder()))
                return type;
        }
        throw new NoSuchElementException();
    }

    public Map<String, String> getEditTypeMap() {
        Map<String, String> editTypes = new HashMap<>();
        for (EditType type : EditType.values()) {
            editTypes.put(String.valueOf(type.getOrder()), getTypeName(type));
        }
        return editTypes;
    }

    public String getTypeName(EditType type) {
        switch (type) {
            case authors:
                return l10nService.getMessage("author");
            case genre:
                return l10nService.getMessage("genre");
            case name:
                return l10nService.getMessage("book_name");
            case lang:
                return l10nService.getMessage("lang");
        }
        return null;
    }
}
