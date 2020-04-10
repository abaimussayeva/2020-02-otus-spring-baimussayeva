package ru.otus.spring.hw.domain.business.dao;

import ru.otus.spring.hw.domain.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookDao {
    long count();
    Book save(Book book);
    Optional<Book> getById(long id);
    List<Book> getAll();
    void delete(long bookId);
    List<Book> searchByName(String search);
}
