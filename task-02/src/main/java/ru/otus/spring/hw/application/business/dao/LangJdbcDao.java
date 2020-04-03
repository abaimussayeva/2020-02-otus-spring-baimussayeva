package ru.otus.spring.hw.application.business.dao;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.spring.hw.application.business.dao.mapper.LangMapper;
import ru.otus.spring.hw.domain.business.dao.LangDao;
import ru.otus.spring.hw.domain.model.Lang;

import java.util.List;
import java.util.Map;

@Repository
public class LangJdbcDao implements LangDao {

    private final NamedParameterJdbcOperations jdbc;

    public LangJdbcDao(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Lang getById(long id) {
        return jdbc.queryForObject(
                "select lang_id, name from languages where lang_id = :id",
                Map.of("id", id),
                new LangMapper());
    }

    @Override
    public List<Lang> getAll() {
        return jdbc.query(
                "select lang_id, name from languages",
                new LangMapper());
    }
}
