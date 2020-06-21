package ru.otus.spring.hw.domain.business.services;

import ru.otus.spring.hw.domain.business.dto.BookDto;
import ru.otus.spring.hw.domain.errors.DBOperationException;

import java.util.List;
import java.util.Optional;

public interface BookService {
    List<BookDto> getAllBooks() throws DBOperationException;
    BookDto save(BookDto book) throws DBOperationException;
    void removeBook(long bookId) throws DBOperationException;
    List<BookDto> searchBookByName(String search) throws DBOperationException;
    Optional<BookDto> getBookById(long bookId) throws DBOperationException;
}
