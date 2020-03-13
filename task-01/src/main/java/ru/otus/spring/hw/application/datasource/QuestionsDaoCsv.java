package ru.otus.spring.hw.application.datasource;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;
import ru.otus.spring.hw.domain.business.dao.QuestionsDao;
import ru.otus.spring.hw.domain.model.Question;
import ru.otus.spring.hw.domain.model.Variant;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

@Repository
public class QuestionsDaoCsv implements QuestionsDao {

    private static final String QUESTION_PREF = "question_";
    private static final String ANSWER_PREF = "q";

    private final String resource;
    private final MessageSource messageSource;

    public QuestionsDaoCsv(@Value("${questions.file}") String resource, MessageSource messageSource) {
        this.resource = resource;
        this.messageSource = messageSource;
    }

    @Override
    public List<Question> getQuestions(Locale locale) throws IOException {
        List<Question> questions = new ArrayList<>();
        try (InputStreamReader input = new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream(resource))) {
            CSVParser csvParser = CSVFormat.EXCEL.withFirstRecordAsHeader().parse(input);
            int i = 1;
            for (CSVRecord record : csvParser) {
                Map<Variant, String> variants = new TreeMap<>();
                for (Variant v: Variant.values()) {
                    variants.put(v, messageSource.getMessage(ANSWER_PREF + v.name() + "_" + i, new Object[]{}, locale));
                }
                questions.add(new Question(messageSource.getMessage(QUESTION_PREF + i, new Object[]{}, locale), variants, record.get("Answer")));
                i++;
            }
        }
        return questions;
    }
}
