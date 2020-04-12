package ru.otus.spring.hw.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "books")
@NamedEntityGraph(name = "book-entity-graph",
        attributeNodes = {@NamedAttributeNode("genre"),
                @NamedAttributeNode("lang"),
                @NamedAttributeNode("authors")})
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id", nullable = false, unique = true)
    private long bookId;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(targetEntity = Genre.class)
    @JoinColumn(name = "genre_id", referencedColumnName = "genre_id")
    private Genre genre;

    @ManyToOne(targetEntity = Lang.class)
    @JoinColumn(name = "lang_id", referencedColumnName = "lang_id")
    private Lang lang;

    @ManyToMany(targetEntity = Author.class, fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "book_authors", joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    private List<Author> authors;

    @OneToMany(targetEntity = Comment.class, fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(name = "book_comments", joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "comment_id"))
    private List<Comment> comments;

    public Book(String name, Genre genre, Lang lang, List<Author> authors) {
        this.name = name;
        this.genre = genre;
        this.lang = lang;
        this.authors = authors;
    }

    public Book(long id, String name, Genre genre, Lang lang, ArrayList<Author> authors) {
        this.bookId = id;
        this.name = name;
        this.genre = genre;
        this.lang = lang;
        this.authors = authors;
    }
}
