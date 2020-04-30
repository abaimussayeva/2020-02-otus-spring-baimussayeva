package ru.otus.spring.hw.application.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import ru.otus.spring.hw.domain.dto.HasName;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Book implements HasName {

    public static final int LAST_COMMENTS_COUNT = 5;

    @Id
    private String bookId;

    private String name;

    @DBRef
    private Genre genre;

    @DBRef
    private Lang lang;

    @DBRef
    private List<Author> authors;

    private List<Comment> comments;

    public Book(String name, Genre genre, Lang lang, List<Author> authors) {
        this.name = name;
        this.genre = genre;
        this.lang = lang;
        this.authors = authors;
    }

    public Book(String id, String name, Genre genre, Lang lang, Author... authors) {
        this.bookId = id;
        this.name = name;
        this.genre = genre;
        this.lang = lang;
        this.authors = List.of(authors);
    }

    public Book(String name, Genre genre, Lang lang, Author... authors) {
        this.name = name;
        this.genre = genre;
        this.lang = lang;
        this.authors = List.of(authors);
    }

    public Book setComments(Comment... comments) {
        this.comments = List.of(comments);
        return this;
    }

    public Book setComments(List<Comment> comments) {
        this.comments = comments;
        return this;
    }
}
