package ru.otus.spring.hw.application.business.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.hw.application.model.Book;

import java.util.List;

public interface BookRepository extends MongoRepository<Book, String> {
    List<Book> findByNameContainingIgnoreCase(String name);
}
