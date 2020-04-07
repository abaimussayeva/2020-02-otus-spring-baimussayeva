package ru.otus.spring.hw.domain.business.services;

import ru.otus.spring.hw.domain.errors.DBOperationException;
import ru.otus.spring.hw.domain.model.Author;
import ru.otus.spring.hw.domain.model.Book;
import ru.otus.spring.hw.domain.model.Comment;
import ru.otus.spring.hw.domain.model.Lang;
import ru.otus.spring.hw.domain.model.dto.BookDto;
import ru.otus.spring.hw.domain.model.dto.GenreDto;

import java.util.List;

public interface LibraryService {
    List<BookDto> getAllBooks() throws DBOperationException;
    BookDto save(Book book) throws DBOperationException;
    void removeBook(long bookId) throws DBOperationException;
    List<Book> searchBookByName(String search) throws DBOperationException;
    List<Lang> getLangs() throws DBOperationException;
    List<GenreDto> getGenres() throws DBOperationException;
    List<Author> getAuthors() throws DBOperationException;
    Comment addComment(long bookId, String comment) throws DBOperationException;
    List<Comment> getComments(long bookId) throws DBOperationException;
}
