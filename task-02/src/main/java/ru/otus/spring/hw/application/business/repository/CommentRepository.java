package ru.otus.spring.hw.application.business.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.spring.hw.domain.model.Comment;

public interface CommentRepository extends CrudRepository<Comment, Long> {

}
