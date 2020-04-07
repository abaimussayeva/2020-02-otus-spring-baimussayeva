package ru.otus.spring.hw.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.spring.hw.domain.model.dto.BookDto;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

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

    public Book(String name, Genre genre, Lang lang, List<Author> authors) {
        this.name = name;
        this.genre = genre;
        this.lang = lang;
        this.authors = authors;
    }

    public BookDto toDto() {
        return new BookDto(bookId, name, genre.getName(), lang.getName(),
                authors.stream().map(Author::getName).collect(Collectors.joining(", ")));
    }
}
