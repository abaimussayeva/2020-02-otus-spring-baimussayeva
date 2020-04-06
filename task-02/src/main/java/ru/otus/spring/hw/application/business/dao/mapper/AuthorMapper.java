package ru.otus.spring.hw.application.business.dao.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.otus.spring.hw.domain.model.Author;

import java.sql.ResultSet;
import java.sql.SQLException;


public class AuthorMapper implements RowMapper<Author> {
    @Override
    public Author mapRow(ResultSet resultSet, int i) throws SQLException {
        long id = resultSet.getLong("author_id");
        String name = resultSet.getString("name");
        String description = resultSet.getString("description");
        return new Author(id, name, description);
    }
}
