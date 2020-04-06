package ru.otus.spring.hw.application.business.dao;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.spring.hw.application.business.dao.mapper.GenreMapper;
import ru.otus.spring.hw.domain.business.dao.GenreDao;
import ru.otus.spring.hw.domain.model.Genre;

import java.util.List;
import java.util.Map;

@Repository
public class GenreJdbcDao implements GenreDao {

    private final NamedParameterJdbcOperations jdbc;

    public GenreJdbcDao(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Genre getById(long id) {
        return jdbc.queryForObject(
                "select genre_id, name from genres where genre_id = :id",
                Map.of("id", id),
                new GenreMapper());
    }

    @Override
    public List<Genre> getAll() {
        List<Genre> genres = jdbc.query(
                "select genre_id, name from genres where parent_id is null",
                new GenreMapper());
        loadChildren(genres);
        return genres;
    }

    private void loadChildren(List<Genre> genres) {
        for (Genre genre: genres) {
            genre.addChildren(jdbc.query(
                    "select genre_id, name from genres where parent_id = :parentId",
                    Map.of("parentId", genre.getGenreId()),
                    new GenreMapper()));
            loadChildren(genre.getChildren());
        }
    }
}
