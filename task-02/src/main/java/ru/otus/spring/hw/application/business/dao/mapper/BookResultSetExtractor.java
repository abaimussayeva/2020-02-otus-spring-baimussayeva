package ru.otus.spring.hw.application.business.dao.mapper;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import ru.otus.spring.hw.domain.model.Author;
import ru.otus.spring.hw.domain.model.Book;
import ru.otus.spring.hw.domain.model.Genre;
import ru.otus.spring.hw.domain.model.Lang;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class BookResultSetExtractor implements ResultSetExtractor<Map<Long, Book>> {
    @Override
    public Map<Long, Book> extractData(ResultSet rs) throws SQLException, DataAccessException {
        Map<Long, Book> books = new HashMap<>();
        while (rs.next()) {
            long id = rs.getLong("book_id");
            Book book = books.get(id);
            if (book == null) {
                book = new Book(id, rs.getString("name"),
                        new Genre(rs.getLong("genre_id"), rs.getString("genre_name")),
                        new Lang(rs.getLong("lang_id"), rs.getString("lang_name")));
                books.put(book.getBookId(), book);
            }
            book.addAuthor(new Author(rs.getLong("author_id"), rs.getString("author_name"),
                    rs.getString("description")));
        }
        return books;
    }
}
