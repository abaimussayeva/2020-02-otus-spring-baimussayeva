package ru.otus.spring.hw.domain.business.services;

import ru.otus.spring.hw.domain.errors.DBOperationException;
import ru.otus.spring.hw.domain.model.Book;
import ru.otus.spring.hw.domain.model.dto.BookDto;

import java.util.List;

public interface BookService {
    List<BookDto> getAllBooks() throws DBOperationException;
    BookDto save(Book book) throws DBOperationException;
    void removeBook(long bookId) throws DBOperationException;
    List<Book> searchBookByName(String search) throws DBOperationException;
    Book getBookById(long bookId) throws DBOperationException;
}
