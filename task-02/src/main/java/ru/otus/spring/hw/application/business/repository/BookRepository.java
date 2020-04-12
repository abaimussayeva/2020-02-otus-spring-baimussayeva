package ru.otus.spring.hw.application.business.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.hw.domain.model.Book;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    @EntityGraph("book-entity-graph")
    List<Book> findAll();
    @EntityGraph("book-entity-graph")
    List<Book> findByNameContainingIgnoreCase(String name);
}
