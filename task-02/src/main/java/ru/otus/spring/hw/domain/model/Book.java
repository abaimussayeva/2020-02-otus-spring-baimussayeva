package ru.otus.spring.hw.domain.model;

import ru.otus.spring.hw.domain.model.dto.BookDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Book {
    private final long bookId;
    private final String name;
    private final Genre genre;
    private final Lang lang;
    private final List<Author> authors;

    public Book(long bookId, String name, Genre genre, Lang lang) {
        this.bookId = bookId;
        this.name = name;
        this.genre = genre;
        this.lang = lang;
        this.authors = new ArrayList<>();
    }

    public long getBookId() {
        return bookId;
    }

    public String getName() {
        return name;
    }

    public Genre getGenre() {
        return genre;
    }

    public Lang getLang() {
        return lang;
    }

    public List<Author> getAuthors() {
        return new ArrayList<>(authors);
    }

    public void addAuthor(Author author ) {
        this.authors.add(author);
    }

    public BookDto toDto() {
        return new BookDto(bookId, name, genre.getName(), lang.getName(),
                authors.stream().map(Author::getName).collect(Collectors.joining(", ")));
    }
}
