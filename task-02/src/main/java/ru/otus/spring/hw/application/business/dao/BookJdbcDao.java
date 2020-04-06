package ru.otus.spring.hw.application.business.dao;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import ru.otus.spring.hw.application.business.dao.mapper.BookResultSetExtractor;
import ru.otus.spring.hw.application.business.dao.mapper.BookMapper;
import ru.otus.spring.hw.domain.business.dao.*;
import ru.otus.spring.hw.domain.model.Book;
import ru.otus.spring.hw.domain.model.entity.BookEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@SuppressWarnings({"SqlNoDataSourceInspection", "ConstantConditions", "SqlDialectInspection"})
@Repository
public class BookJdbcDao implements BookDao {

    private final NamedParameterJdbcOperations jdbc;
    private final BookAuthorsDao bookAuthorsDao;

    public BookJdbcDao(NamedParameterJdbcOperations jdbc, BookAuthorsDao bookAuthorsDao) {
        this.jdbc = jdbc;
        this.bookAuthorsDao = bookAuthorsDao;
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
        bookAuthorsDao.insertAuthors(newId, book.getAuthors());
        return getById(newId);
    }

    @Override
    public Book getById(long id) {
        return jdbc.queryForObject(
                "select b.book_id, b.name, b.genre_id, g.name as genre_name, b.lang_id, l.name as lang_name, " +
                        " ba.author_id, a.name as author_name, a.description " +
                        " from books b " +
                        " inner join genres g on b.genre_id = g.genre_id " +
                        " inner join languages l on b.lang_id = l.lang_id " +
                        " inner join book_authors ba on b.book_id = ba.book_id" +
                        " inner join authors a on ba.author_id = a.author_id " +
                        " where b.book_id = :id",
                Map.of("id", id),
                new BookMapper());
    }

    @Override
    public List<Book> getAll() {
        Map<Long, Book> bookMap = jdbc.query(
                "select b.book_id, b.name, b.genre_id, g.name as genre_name, b.lang_id, l.name as lang_name, " +
                        " ba.author_id, a.name as author_name, a.description " +
                        " from books b " +
                        " inner join genres g on b.genre_id = g.genre_id " +
                        " inner join languages l on b.lang_id = l.lang_id " +
                        " inner join book_authors ba on b.book_id = ba.book_id" +
                        " inner join authors a on ba.author_id = a.author_id ",
                new BookResultSetExtractor());
        return new ArrayList<>(Objects.requireNonNull(bookMap).values());
    }

    @Override
    public void deleteById(long id) {
        jdbc.update("delete from books where book_id = :id", Map.of("id", id));
        bookAuthorsDao.deleteAuthors(id);
    }

    @Override
    public List<Book> searchByName(String search) {
        if (!StringUtils.hasText(search)) {
            return getAll();
        }
        Map<Long, Book> bookMap = jdbc.query(
                "select b.book_id, b.name, b.genre_id, g.name as genre_name, b.lang_id, l.name as lang_name, " +
                        " ba.author_id, a.name as author_name, a.description " +
                        " from books b " +
                        " inner join genres g on b.genre_id = g.genre_id " +
                        " inner join languages l on b.lang_id = l.lang_id " +
                        " inner join book_authors ba on b.book_id = ba.book_id" +
                        " inner join authors a on ba.author_id = a.author_id " +
                        " where lower(b.name) like lower(:search)",
                Map.of("search", "%" + search + "%"),
                new BookResultSetExtractor());
        return new ArrayList<>(Objects.requireNonNull(bookMap).values());
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
        bookAuthorsDao.deleteAuthors(book.getBookId());
        bookAuthorsDao.insertAuthors(book.getBookId(), book.getAuthors());
        return getById(book.getBookId());
    }
}
