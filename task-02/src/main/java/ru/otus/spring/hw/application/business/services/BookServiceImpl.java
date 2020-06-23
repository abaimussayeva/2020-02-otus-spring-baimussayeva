package ru.otus.spring.hw.application.business.services;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.hw.application.business.mapper.BookMapper;
import ru.otus.spring.hw.application.business.repository.BookRepository;
import ru.otus.spring.hw.domain.business.dto.BookDto;
import ru.otus.spring.hw.domain.business.services.BookService;
import ru.otus.spring.hw.domain.errors.DBOperationException;
import ru.otus.spring.hw.domain.model.Book;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper mapper;

    public BookServiceImpl(BookRepository bookRepository, BookMapper mapper) {
        this.bookRepository = bookRepository;
        this.mapper = mapper;
    }

    @Override
    public List<BookDto> getAllBooks() throws DBOperationException {
        try {
            return bookRepository.findAll()
                    .stream()
                    .map(mapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new DBOperationException("Reading books error", e);
        }
    }

    @Override
    public BookDto save(BookDto book) throws DBOperationException {
        try {
            Book inserted = bookRepository.save(mapper.toEntity(book));
            return mapper.toDto(inserted);
        } catch (Exception e) {
            throw new DBOperationException("Insert book error", e);
        }
    }

    @Override
    public void removeBook(long bookId) throws DBOperationException {
        try {
            bookRepository.deleteById(bookId);
        } catch (Exception e) {
            throw new DBOperationException("Delete book error", e);
        }
    }

    @Override
    public List<BookDto> searchBookByName(String search) throws DBOperationException {
        try {
            return bookRepository.findByNameContainingIgnoreCase(search)
                    .stream()
                    .map(mapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new DBOperationException("Book search error", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BookDto> getBookById(long bookId) throws DBOperationException {
        try {
            Optional<Book> book = bookRepository.findById(bookId);
            if (book.isEmpty()) {
                return Optional.empty();
            }
            Hibernate.initialize(book.get().getComments());
            return Optional.of(mapper.toDto(book.get()));
        } catch (Exception e) {
            throw new DBOperationException("Cannot load book from repository", e);
        }
    }
}
