package ru.otus.spring.hw.application.datasource;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Repository;
import ru.otus.spring.hw.application.config.LocaleProps;
import ru.otus.spring.hw.domain.business.dao.QuestionsDao;
import ru.otus.spring.hw.domain.errors.QuestionLoadException;
import ru.otus.spring.hw.domain.model.Question;
import ru.otus.spring.hw.domain.model.Variant;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Repository
public class QuestionsDaoCsv implements QuestionsDao {

    private final LocaleProps props;

    public QuestionsDaoCsv(LocaleProps props) {
        this.props = props;
    }

    @Override
    public List<Question> getQuestions() throws QuestionLoadException {
        List<Question> questions = new ArrayList<>();
        try (InputStreamReader input = new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream(props.getQuestionFile()))) {
            CSVParser csvParser = CSVFormat.EXCEL.withFirstRecordAsHeader().parse(input);
            for (CSVRecord record : csvParser) {
                Map<Variant, String> variants = new TreeMap<>();
                for (Variant v: Variant.values()) {
                    variants.put(v, record.get(v.name()));
                }
                questions.add(new Question(record.get("Question"), variants, record.get("Answer")));
            }
        } catch (Exception e) {
            throw new QuestionLoadException("Error occurred while questions load", e);
        }
        return questions;
    }
}
