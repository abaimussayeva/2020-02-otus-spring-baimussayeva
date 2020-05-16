package ru.otus.spring.hw.domain.business.services;

import ru.otus.spring.hw.application.model.Book;
import ru.otus.spring.hw.domain.dto.BookDto;
import ru.otus.spring.hw.domain.errors.DBOperationException;

import java.util.List;

public interface BookService {
    List<BookDto> getAllBooks() throws DBOperationException;
    BookDto save(Book book) throws DBOperationException;
    void removeBook(String bookId) throws DBOperationException;
    List<Book> searchBookByName(String search) throws DBOperationException;
    Book getBookById(String bookId) throws DBOperationException;
}
