package ru.otus.spring.hw.application.business.services;

import org.springframework.stereotype.Service;
import ru.otus.spring.hw.domain.business.dao.AuthorDao;
import ru.otus.spring.hw.domain.business.dao.BookDao;
import ru.otus.spring.hw.domain.business.dao.GenreDao;
import ru.otus.spring.hw.domain.business.dao.LangDao;
import ru.otus.spring.hw.domain.business.services.BaseService;
import ru.otus.spring.hw.domain.errors.DBOperationException;
import ru.otus.spring.hw.domain.model.Author;
import ru.otus.spring.hw.domain.model.Book;
import ru.otus.spring.hw.domain.model.Genre;
import ru.otus.spring.hw.domain.model.Lang;
import ru.otus.spring.hw.domain.model.dto.BookDto;
import ru.otus.spring.hw.domain.model.entity.BookEntity;

import java.util.ArrayList;
import java.util.List;

@Service
public class BaseServiceImpl implements BaseService {

    private final BookDao bookDao;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;
    private final LangDao langDao;

    public BaseServiceImpl(BookDao bookDao, AuthorDao authorDao, GenreDao genreDao, LangDao langDao) {
        this.bookDao = bookDao;
        this.authorDao = authorDao;
        this.genreDao = genreDao;
        this.langDao = langDao;
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
    public BookDto addBook(BookEntity book) throws DBOperationException {
        try {
            Book inserted = bookDao.insert(book);
            return inserted.toDto();
        } catch (Exception e) {
            throw new DBOperationException("Insert book error", e);
        }
    }

    @Override
    public BookDto editBook(BookEntity book) throws DBOperationException {
        try {
            return bookDao.update(book).toDto();
        } catch (Exception e) {
            throw new DBOperationException("Update book error", e);
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
    public List<Genre> getGenres() throws DBOperationException {
        try {
            return genreDao.getAll();
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
}
