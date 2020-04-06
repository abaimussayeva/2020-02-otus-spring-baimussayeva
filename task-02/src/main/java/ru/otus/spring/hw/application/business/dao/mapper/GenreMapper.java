package ru.otus.spring.hw.application.business.dao.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.otus.spring.hw.domain.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GenreMapper implements RowMapper<Genre> {
    @Override
    public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
        long id = resultSet.getLong("genre_id");
        String name = resultSet.getString("name");
        return new Genre(id, name);
    }
}
