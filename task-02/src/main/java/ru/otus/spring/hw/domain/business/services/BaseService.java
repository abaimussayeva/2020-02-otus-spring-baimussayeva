package ru.otus.spring.hw.domain.business.services;

import ru.otus.spring.hw.domain.errors.DBOperationException;
import ru.otus.spring.hw.domain.model.Author;
import ru.otus.spring.hw.domain.model.Book;
import ru.otus.spring.hw.domain.model.Genre;
import ru.otus.spring.hw.domain.model.Lang;
import ru.otus.spring.hw.domain.model.dto.BookDto;
import ru.otus.spring.hw.domain.model.entity.BookEntity;

import java.util.List;

public interface BaseService {
    List<BookDto> getAllBooks() throws DBOperationException;
    BookDto addBook(BookEntity book) throws DBOperationException;
    BookDto editBook(BookEntity bookEntity) throws DBOperationException;
    void removeBook(long bookId) throws DBOperationException;
    List<Book> searchBookByName(String search) throws DBOperationException;
    List<Lang> getLangs() throws DBOperationException;
    List<Genre> getGenres() throws DBOperationException;
    List<Author> getAuthors() throws DBOperationException;
}
