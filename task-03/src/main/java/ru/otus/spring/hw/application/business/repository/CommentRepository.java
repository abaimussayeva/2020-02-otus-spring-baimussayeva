package ru.otus.spring.hw.application.business.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import ru.otus.spring.hw.application.model.Comment;

public interface CommentRepository extends ReactiveMongoRepository<Comment, String> {
    Flux<Comment> findByBookIdOrderByCreatedDesc(String bookId);
}
