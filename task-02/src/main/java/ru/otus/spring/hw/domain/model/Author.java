package ru.otus.spring.hw.domain.model;

public class Author {
    private final long authorId;
    private final String name;
    private final String description;

    public Author(long authorId, String name, String description) {
        this.authorId = authorId;
        this.name = name;
        this.description = description;
    }

    public long getAuthorId() {
        return authorId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
