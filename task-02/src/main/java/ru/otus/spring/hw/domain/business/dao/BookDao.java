package ru.otus.spring.hw.domain.business.dao;

import ru.otus.spring.hw.domain.model.Book;
import ru.otus.spring.hw.domain.model.entity.BookEntity;

import java.util.List;

public interface BookDao {
    int count();
    Book insert(BookEntity book);
    Book getById(long id);
    List<Book> getAll();
    void deleteById(long id);
    List<Book> searchByName(String search);
    Book update(BookEntity book);
}
