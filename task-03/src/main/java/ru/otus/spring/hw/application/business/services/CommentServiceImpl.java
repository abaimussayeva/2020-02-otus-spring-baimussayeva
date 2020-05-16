package ru.otus.spring.hw.application.business.services;

import org.springframework.stereotype.Service;
import ru.otus.spring.hw.application.business.repository.BookRepository;
import ru.otus.spring.hw.application.business.repository.CommentRepository;
import ru.otus.spring.hw.application.model.Book;
import ru.otus.spring.hw.application.model.Comment;
import ru.otus.spring.hw.domain.business.services.CommentService;
import ru.otus.spring.hw.domain.errors.DBOperationException;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final BookRepository bookRepository;

    public CommentServiceImpl(CommentRepository commentRepository, BookRepository bookRepository) {
        this.commentRepository = commentRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public Book addComment(Book book, String comment) throws DBOperationException {
        try {
            Comment saved = commentRepository.save(new Comment(book.getBookId(), comment));
            book.getComments().add(0, saved);
            if (book.getComments().size() > Book.LAST_COMMENTS_COUNT) {
                for (int i = 0; i < book.getComments().size() - Book.LAST_COMMENTS_COUNT; i++) {
                    book.getComments().remove(book.getComments().size() - 1);
                }
            }
            return bookRepository.save(book);
        } catch (Exception e) {
            throw new DBOperationException("Comment add error", e);
        }
    }

    @Override
    public List<Comment> getBookComments(String bookId) {
        return commentRepository.findByBookIdOrderByCreatedDesc(bookId);
    }
}
