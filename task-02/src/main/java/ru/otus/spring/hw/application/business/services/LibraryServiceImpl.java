package ru.otus.spring.hw.application.business.services;

import org.springframework.stereotype.Service;
import ru.otus.spring.hw.domain.business.dao.*;
import ru.otus.spring.hw.domain.business.services.LibraryService;
import ru.otus.spring.hw.domain.errors.DBOperationException;
import ru.otus.spring.hw.domain.model.Author;
import ru.otus.spring.hw.domain.model.Book;
import ru.otus.spring.hw.domain.model.Comment;
import ru.otus.spring.hw.domain.model.Lang;
import ru.otus.spring.hw.domain.model.dto.BookDto;
import ru.otus.spring.hw.domain.model.dto.GenreDto;

import java.util.ArrayList;
import java.util.List;

@Service
public class LibraryServiceImpl implements LibraryService {

    private final BookDao bookDao;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;
    private final LangDao langDao;
    private final CommentDao commentDao;

    public LibraryServiceImpl(BookDao bookDao, AuthorDao authorDao, GenreDao genreDao, LangDao langDao, CommentDao commentDao) {
        this.bookDao = bookDao;
        this.authorDao = authorDao;
        this.genreDao = genreDao;
        this.langDao = langDao;
        this.commentDao = commentDao;
    }

    @Override
    public List<BookDto> getAllBooks() throws DBOperationException {
        try {
            List<Book> books = bookDao.getAll();
            List<BookDto> bookDtoList = new ArrayList<>();
            books.forEach(b -> bookDtoList.add(b.toDto()));
            return bookDtoList;
        } catch (Exception e) {
            throw new DBOperationException("Reading books error", e);
        }
    }

    @Override
    public BookDto save(Book book) throws DBOperationException {
        try {
            Book inserted = bookDao.save(book);
            return inserted.toDto();
        } catch (Exception e) {
            throw new DBOperationException("Insert book error", e);
        }
    }

    @Override
    public void removeBook(long bookId) throws DBOperationException {
        try {
            bookDao.deleteById(bookId);
        } catch (Exception e) {
            throw new DBOperationException("Delete book error", e);
        }
    }

    @Override
    public List<Book> searchBookByName(String search) throws DBOperationException {
        try {
            return bookDao.searchByName(search);
        } catch (Exception e) {
            throw new DBOperationException("Book search error", e);
        }
    }

    @Override
    public List<Lang> getLangs() throws DBOperationException {
        try {
            return langDao.getAll();
        } catch (Exception e) {
            throw new DBOperationException("Languages get error", e);
        }
    }

    @Override
    public List<GenreDto> getGenres() throws DBOperationException {
        try {
            return genreDao.getAllTree();
        } catch (Exception e) {
            throw new DBOperationException("Genres get error", e);
        }
    }

    @Override
    public List<Author> getAuthors() throws DBOperationException {
        try {
            return authorDao.getAll();
        } catch (Exception e) {
            throw new DBOperationException("Authors get error", e);
        }
    }

    @Override
    public Comment addComment(long bookId, String comment) throws DBOperationException {
        try {
            return commentDao.addComment(new Comment(bookId, comment));
        } catch (Exception e) {
            throw new DBOperationException("Comment add error", e);
        }
    }

    @Override
    public List<Comment> getComments(long bookId) throws DBOperationException {
        try {
            return commentDao.getCommentsForBook(bookId);
        } catch (Exception e) {
            throw new DBOperationException("Comments get error", e);
        }
    }
}
