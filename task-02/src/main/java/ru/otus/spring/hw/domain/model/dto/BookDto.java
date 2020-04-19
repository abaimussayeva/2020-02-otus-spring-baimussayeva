package ru.otus.spring.hw.domain.model.dto;

import ru.otus.spring.hw.domain.model.Author;
import ru.otus.spring.hw.domain.model.Book;

import java.util.stream.Collectors;

public class BookDto {
    private final long bookId;
    private final String name;
    private final String genre;
    private final String language;
    private final String authors;

    public static BookDto fromBook(Book book) {
        return new BookDto(book.getBookId(), book.getName(), book.getGenre().getName(), book.getLang().getName(),
                book.getAuthors().stream().map(Author::getName).collect(Collectors.joining(", ")));
    }

    public BookDto(long bookId, String name, String genre, String language, String authors) {
        this.bookId = bookId;
        this.name = name;
        this.genre = genre;
        this.language = language;
        this.authors = authors;
    }

    public long getBookId() {
        return bookId;
    }

    public String getName() {
        return name;
    }

    public String getGenre() {
        return genre;
    }

    public String getLanguage() {
        return language;
    }

    public String getAuthors() {
        return authors;
    }
}
