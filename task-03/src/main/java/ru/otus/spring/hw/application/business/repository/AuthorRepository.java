package ru.otus.spring.hw.application.business.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.otus.spring.hw.application.model.Author;

public interface AuthorRepository extends ReactiveMongoRepository<Author, String> {

}
