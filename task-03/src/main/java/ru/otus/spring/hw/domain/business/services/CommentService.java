package ru.otus.spring.hw.domain.business.services;

import ru.otus.spring.hw.application.model.Book;
import ru.otus.spring.hw.application.model.Comment;
import ru.otus.spring.hw.domain.errors.DBOperationException;

import java.util.List;

public interface CommentService {
    Book addComment(Book book, String comment) throws DBOperationException;
    List<Comment> getBookComments(String bookId);
}
