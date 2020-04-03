package ru.otus.spring.hw.application.business.dao;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.spring.hw.application.business.dao.mapper.AuthorMapper;
import ru.otus.spring.hw.domain.business.dao.AuthorDao;
import ru.otus.spring.hw.domain.model.Author;

import java.util.List;
import java.util.Map;

@SuppressWarnings({"SqlNoDataSourceInspection", "SqlDialectInspection"})
@Repository
public class AuthorJdbcDao implements AuthorDao {

    private final NamedParameterJdbcOperations jdbc;

    public AuthorJdbcDao(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Author getById(long id) {
        return jdbc.queryForObject(
                "select author_id, name, description from authors where author_id = :id",
                Map.of("id", id),
                new AuthorMapper());
    }

    @Override
    public List<Author> getAll() {
        return jdbc.query(
                "select author_id, name, description from authors order by name",
                new AuthorMapper());
    }
}
