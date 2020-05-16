package ru.otus.spring.hw.application.business.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.hw.application.business.repository.BookRepository;
import ru.otus.spring.hw.application.model.Book;
import ru.otus.spring.hw.domain.business.services.BookService;
import ru.otus.spring.hw.domain.dto.BookDto;
import ru.otus.spring.hw.domain.errors.DBOperationException;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<BookDto> getAllBooks() throws DBOperationException {
        try {
            List<Book> books = bookRepository.findAll();
            List<BookDto> bookDtoList = new ArrayList<>();
            books.forEach(b -> bookDtoList.add(BookDto.fromBook(b)));
            return bookDtoList;
        } catch (Exception e) {
            throw new DBOperationException("Reading books error", e);
        }
    }

    @Override
    public BookDto save(Book book) throws DBOperationException {
        try {
            Book inserted = bookRepository.save(book);
            return BookDto.fromBook(inserted);
        } catch (Exception e) {
            throw new DBOperationException("Insert book error", e);
        }
    }

    @Override
    public void removeBook(String bookId) throws DBOperationException {
        try {
            bookRepository.deleteById(bookId);
        } catch (Exception e) {
            throw new DBOperationException("Delete book error", e);
        }
    }

    @Override
    public List<Book> searchBookByName(String search) throws DBOperationException {
        try {
            return bookRepository.findByNameContainingIgnoreCase(search);
        } catch (Exception e) {
            throw new DBOperationException("Book search error", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Book getBookById(String bookId) throws DBOperationException {
        try {
            Book book = bookRepository.findById(bookId).orElseThrow();
            return book;
        } catch (Exception e) {
            throw new DBOperationException("Cannot load book from repository", e);
        }
    }
}
