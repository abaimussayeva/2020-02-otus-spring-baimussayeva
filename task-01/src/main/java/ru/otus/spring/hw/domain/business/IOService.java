package ru.otus.spring.hw.domain.business;

import org.springframework.util.StringUtils;
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
                String defaultMarker = null;
                if (defaultValue != null) {
                    if (option.getKey().equals(defaultValue)) {
                        defaultMarker = "*";
                    }
                }
                if (defaultMarker != null) {
                    out(String.format("%s [%s] %s ", defaultMarker, option.getKey(), option.getValue()));
                } else {
                    out(String.format("  [%s] %s", option.getKey(), option.getValue()));
                }
            }
            answer = readString(promptMessage);
        } while (!StringUtil.containsString(allowedAnswers, answer, true) && !answer.isEmpty());

        if (StringUtils.isEmpty(answer) && allowedAnswers.contains("")) {
            return defaultValue;
        }
        return answer;
    }
}
