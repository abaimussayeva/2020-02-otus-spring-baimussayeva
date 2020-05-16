package ru.otus.spring.hw.application.business.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.spring.hw.application.model.Comment;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment, String> {
    List<Comment> findByBookIdOrderByCreatedDesc(String bookId);
}
