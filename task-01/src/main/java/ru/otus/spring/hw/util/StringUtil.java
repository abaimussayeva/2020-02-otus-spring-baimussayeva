package ru.otus.spring.hw.util;

import java.util.Set;

public class StringUtil {
    public static boolean containsString(Set<String> l, String s, boolean ignoreCase){
        if (!ignoreCase) {
            return l.contains(s);
        }
        for (String value : l) {
            if (value.equalsIgnoreCase(s)) {
                return true;
            }
        }
        return false;
    }
}
