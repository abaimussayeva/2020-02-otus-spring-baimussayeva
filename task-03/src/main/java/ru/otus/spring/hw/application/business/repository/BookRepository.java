package ru.otus.spring.hw.application.business.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import ru.otus.spring.hw.application.model.Book;

public interface BookRepository extends ReactiveMongoRepository<Book, String> {
    Flux<Book> findByNameContainingIgnoreCase(String name);
}
