package ru.otus.spring.hw.application.business.dao;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.otus.spring.hw.domain.business.dao.BookAuthorsDao;

import java.util.List;
import java.util.Map;

@Repository
public class BookAuthorsJdbcDao implements BookAuthorsDao {

    private final NamedParameterJdbcOperations jdbc;

    public BookAuthorsJdbcDao(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public void deleteAuthors(long bookId) {
        jdbc.update("delete from book_authors where book_id = :id", Map.of("id", bookId));
    }

    @Override
    public void insertAuthors(long bookId, List<Long> authors) {
        SqlParameterSource[] authorParams = new SqlParameterSource[authors.size()];
        for (int i = 0; i < authors.size(); i++) {
            authorParams[i] = new MapSqlParameterSource()
                    .addValue("book_id", bookId)
                    .addValue("author_id", authors.get(i));
        }
        jdbc.batchUpdate("insert into book_authors(book_id, author_id) values(:book_id, :author_id)", authorParams);
    }
}
