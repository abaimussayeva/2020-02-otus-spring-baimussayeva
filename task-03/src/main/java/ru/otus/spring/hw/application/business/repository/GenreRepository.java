package ru.otus.spring.hw.application.business.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.hw.application.model.Genre;

import java.util.List;

public interface GenreRepository extends MongoRepository<Genre, String> {
    List<Genre> findByNameContains(String name);
}
