package ru.otus.spring.hw.domain.business.services;

import ru.otus.spring.hw.domain.errors.DBOperationException;
import ru.otus.spring.hw.domain.model.Book;
import ru.otus.spring.hw.domain.model.Comment;

public interface CommentService {
    Comment addComment(Book book, String comment) throws DBOperationException;
}
