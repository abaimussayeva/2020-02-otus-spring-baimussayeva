package ru.otus.spring.hw.domain.business;

import org.springframework.util.StringUtils;
import ru.otus.spring.hw.domain.model.entity.BookEntity;
import ru.otus.spring.hw.util.StringUtil;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public interface IOService {
    void out(String message);
    String readString(String prompt);
    void printWarning(String message);
    void printError(String message);
    void printSuccess(String message);
    void printInfo(String message);
    void printKeyValue(String key, String value);

    /**
     * Код заимствован из статьи https://medium.com/agency04/developing-cli-application-with-spring-shell-part-2-4be6ce252678
     */
    default String selectFromList(String headingMessage, String promptMessage,
                                  Map<String, String> options, String defaultValue) {
        String answer;
        Set<String> allowedAnswers = new HashSet<>(options.keySet());

        out(headingMessage + ": ");
        do {
            for (Map.Entry<String, String> option: options.entrySet()) {
                printOptions(option.getKey(), option.getValue(), defaultValue);
            }
            answer = readString(promptMessage);
        } while (!StringUtil.containsString(allowedAnswers, answer, true) && !answer.isEmpty());

        if (StringUtils.isEmpty(answer) && allowedAnswers.contains("")) {
            return defaultValue;
        }
        return answer;
    }

    /**
     * Код заимствован из статьи https://medium.com/agency04/developing-cli-application-with-spring-shell-part-2-4be6ce252678
     */
    default Long selectLongFromList(String headingMessage, String promptMessage,
                                    Map<Long, String> options, Long defaultValue) {
        Long answer;
        Set<Long> allowedAnswers = new HashSet<>(options.keySet());

        out(headingMessage + ": ");
        do {
            for (Map.Entry<Long, String> option: options.entrySet()) {
                printOptions(option.getKey(), option.getValue(), defaultValue);
            }
            try {
                answer = Long.parseLong(readString(promptMessage));
            } catch (NumberFormatException e) {
                answer = null;
            }
        } while (answer != null && !allowedAnswers.contains(answer));

        if (StringUtils.isEmpty(answer) && allowedAnswers.contains(BookEntity.NEW_BOOK_ID)) {
            return defaultValue;
        }
        return answer;
    }

    default void printOptions(Object key, String value, Object defaultValue) {
        String defaultMarker = null;
        if (defaultValue != null) {
            if (key.equals(defaultValue)) {
                defaultMarker = "*";
            }
        }
        if (defaultMarker != null) {
            out(String.format("%s [%s] %s ", defaultMarker, key, value));
        } else {
            out(String.format("  [%s] %s", key, value));
        }
    }
}
