package ru.otus.spring.hw.application.business.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import ru.otus.spring.hw.domain.business.dao.AuthorDao;
import ru.otus.spring.hw.domain.business.dao.BookDao;
import ru.otus.spring.hw.domain.business.dao.GenreDao;
import ru.otus.spring.hw.domain.business.dao.LangDao;
import ru.otus.spring.hw.domain.model.Book;
import ru.otus.spring.hw.domain.model.entity.BookEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"SqlNoDataSourceInspection", "ConstantConditions", "SqlDialectInspection"})
@Repository
public class BookJdbcDao implements BookDao {

    private final NamedParameterJdbcOperations jdbc;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;
    private final LangDao langDao;

    public BookJdbcDao(NamedParameterJdbcOperations jdbc, AuthorDao authorDao, GenreDao genreDao, LangDao langDao) {
        this.jdbc = jdbc;
        this.authorDao = authorDao;
        this.genreDao = genreDao;
        this.langDao = langDao;
    }

    public int count() {
        return jdbc.getJdbcOperations().queryForObject("select count(*) from books", Integer.class);
    }

    @Override
    public Book insert(BookEntity book) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("name", book.getName())
                .addValue("genre", book.getGenre())
                .addValue("lang", book.getLanguage());
        jdbc.update("insert into books(name, genre_id, lang_id) values(:name, :genre, :lang)",
                parameters, keyHolder);
        long newId = keyHolder.getKey().longValue();
        insertAuthors(book, newId);
        return getById(newId);
    }

    @Override
    public Book getById(long id) {
        return jdbc.queryForObject(
                "select book_id, name, genre_id, lang_id from books where book_id = :id",
                Map.of("id", id),
                new BookMapper());
    }

    @Override
    public List<Book> getAll() {
        return jdbc.query(
                "select book_id, name, genre_id, lang_id from books",
                new BookMapper());
    }

    @Override
    public void deleteById(long id) {
        jdbc.update("delete from books where book_id = :id", Map.of("id", id));
        jdbc.update("delete from book_authors where book_id = :id", Map.of("id", id));
    }

    @Override
    public List<Book> searchByName(String search) {
        if (!StringUtils.hasText(search)) {
            return getAll();
        }
        return jdbc.query(
                "select book_id, name, genre_id, lang_id from books where lower(name) like lower(:search)",
                Map.of("search", "%" + search + "%"),
                new BookMapper());
    }

    @Override
    public Book update(BookEntity book) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("name", book.getName())
                .addValue("genre", book.getGenre())
                .addValue("lang", book.getLanguage())
                .addValue("id", book.getBookId());
        jdbc.update("update books set name = :name, genre_id = :genre, lang_id = :lang where book_id = :id",
                parameters);
        jdbc.update("delete from book_authors where book_id = :id", Map.of("id", book.getBookId()));
        insertAuthors(book, book.getBookId());
        return getById(book.getBookId());
    }

    private void insertAuthors(BookEntity book, long bookId) {
        SqlParameterSource[] authorParams = new SqlParameterSource[book.getAuthors().size()];
        for (int i = 0; i < book.getAuthors().size(); i++) {
            authorParams[i] = new MapSqlParameterSource()
                    .addValue("book_id", bookId)
                    .addValue("author_id", book.getAuthors().get(i));
        }
        jdbc.batchUpdate("insert into book_authors(book_id, author_id) values(:book_id, :author_id)", authorParams);
    }

    private class BookMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            Book book = new Book(
                    resultSet.getLong("book_id"),
                    resultSet.getString("name"),
                    genreDao.getById(resultSet.getLong("genre_id")),
                    langDao.getById(resultSet.getLong("lang_id"))
            );
            jdbc.query("select author_id from book_authors where book_id = :id ",
                    Map.of("id", book.getBookId()),
                    rs -> {
                        book.addAuthor(authorDao.getById(rs.getLong("author_id")));
                    });
            return book;
        }
    }
}
