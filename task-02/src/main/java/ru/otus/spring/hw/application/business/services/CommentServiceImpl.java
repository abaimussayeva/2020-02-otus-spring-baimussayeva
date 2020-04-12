package ru.otus.spring.hw.application.business.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.hw.application.business.repository.CommentRepository;
import ru.otus.spring.hw.domain.business.services.CommentService;
import ru.otus.spring.hw.domain.errors.DBOperationException;
import ru.otus.spring.hw.domain.model.Book;
import ru.otus.spring.hw.domain.model.Comment;

@Service
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public Comment addComment(Book book, String comment) throws DBOperationException {
        try {
            return commentRepository.save(new Comment(book.getBookId(), comment));
        } catch (Exception e) {
            throw new DBOperationException("Comment add error", e);
        }
    }
}
