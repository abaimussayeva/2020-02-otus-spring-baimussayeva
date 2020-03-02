package ru.otus.spring.hw.dao;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import ru.otus.spring.hw.domain.Question;
import ru.otus.spring.hw.domain.Variant;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class QuestionsDaoCsv implements QuestionsDao {

    private final String resource;

    public QuestionsDaoCsv(String resource) {
        this.resource = resource;
    }

    @Override
    public List<Question> getQuestions() throws IOException {
        List<Question> questions = new ArrayList<>();
        try (InputStreamReader input = new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream(resource))) {
            CSVParser csvParser = CSVFormat.EXCEL.withFirstRecordAsHeader().parse(input);
            for (CSVRecord record : csvParser) {
                Map<Variant, String> variants = new TreeMap<>();
                for (Variant v: Variant.values()) {
                    variants.put(v, record.get(v.name()));
                }
                questions.add(new Question(record.get("Question"), variants, record.get("Answer")));
            }

        }
        return questions;
    }
}
