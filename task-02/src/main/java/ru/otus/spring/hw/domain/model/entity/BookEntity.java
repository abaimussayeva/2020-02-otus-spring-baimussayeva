package ru.otus.spring.hw.domain.model.entity;

import java.util.List;

public class BookEntity {

    public static final long NEW_BOOK_ID = -1;

    private final long bookId;
    private final String name;
    private final long genre;
    private final long language;
    private final List<Long> authors;

    public BookEntity(long bookId, String name, long genre, long language, List<Long> authors) {
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

    public long getGenre() {
        return genre;
    }

    public long getLanguage() {
        return language;
    }

    public List<Long> getAuthors() {
        return authors;
    }
}
