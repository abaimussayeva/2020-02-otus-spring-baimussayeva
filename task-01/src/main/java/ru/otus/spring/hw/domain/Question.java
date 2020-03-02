package ru.otus.spring.hw.domain;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class Question {

    private final String question;
    private final Map<Variant, String> variants;
    private final String answer;

    public Question(String question, Map<Variant, String> variants, String answer) {
        this.question = question;
        if (variants == null) {
            throw new RuntimeException("Variants cannot be null");
        }
        this.variants = new TreeMap<>(variants);
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public Set<Variant> getKeys() {
        return new TreeSet<>(variants.keySet());
    }

    public String getVariant(Variant variant) {
        return variants.get(variant);
    }

    public String getPrintQuestion() {
        StringBuilder builder = new StringBuilder();
        builder.append(question).append("\n");
        for (Variant v: Variant.values()) {
            builder.append(v.name()).append(") ").append(getVariant(v)).append("\n");
        }
        builder.delete(builder.length() - 1, builder.length());
        return builder.toString();
    }




}
