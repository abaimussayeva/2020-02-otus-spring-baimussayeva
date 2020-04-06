package ru.otus.spring.hw.application.business.dao.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.otus.spring.hw.domain.model.Lang;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LangMapper implements RowMapper<Lang> {
    @Override
    public Lang mapRow(ResultSet resultSet, int i) throws SQLException {
        long id = resultSet.getLong("lang_id");
        String name = resultSet.getString("name");
        return new Lang(id, name);
    }
}
