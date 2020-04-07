package ru.otus.spring.hw.domain.business.dao;

import ru.otus.spring.hw.domain.model.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentDao {
    Optional<Comment> getCommentById(long id);
    Comment addComment(Comment comment);
    List<Comment> getCommentsForBook(long bookId);
}
