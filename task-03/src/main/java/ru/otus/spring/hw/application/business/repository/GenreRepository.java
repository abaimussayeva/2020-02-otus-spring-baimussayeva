package ru.otus.spring.hw.application.business.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import ru.otus.spring.hw.application.model.Genre;

public interface GenreRepository extends ReactiveMongoRepository<Genre, String> {
    Flux<Genre> findByNameContains(String name);
}
