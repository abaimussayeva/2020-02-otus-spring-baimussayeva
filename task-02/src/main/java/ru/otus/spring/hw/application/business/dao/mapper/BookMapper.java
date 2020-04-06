package ru.otus.spring.hw.application.business.dao.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.otus.spring.hw.domain.model.Author;
import ru.otus.spring.hw.domain.model.Book;
import ru.otus.spring.hw.domain.model.Genre;
import ru.otus.spring.hw.domain.model.Lang;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookMapper implements RowMapper<Book> {

    @Override
    public Book mapRow(ResultSet rs, int i) throws SQLException {
        long id = rs.getLong("book_id");
        Book book = new Book(id, rs.getString("name"),
                new Genre(rs.getLong("genre_id"), rs.getString("genre_name")),
                new Lang(rs.getLong("lang_id"), rs.getString("lang_name")));
        book.addAuthor(new Author(rs.getLong("author_id"), rs.getString("author_name"),
                rs.getString("description")));
        while (rs.next()) {
            book.addAuthor(new Author(rs.getLong("author_id"), rs.getString("author_name"),
                    rs.getString("description")));
        }
        return book;
    }
}
