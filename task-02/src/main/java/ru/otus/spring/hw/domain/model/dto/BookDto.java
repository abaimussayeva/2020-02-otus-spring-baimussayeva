package ru.otus.spring.hw.domain.model.dto;

public class BookDto {
    private final long bookId;
    private final String name;
    private final String genre;
    private final String language;
    private final String authors;

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
