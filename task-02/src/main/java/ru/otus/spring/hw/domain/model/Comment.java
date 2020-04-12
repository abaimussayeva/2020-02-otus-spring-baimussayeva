package ru.otus.spring.hw.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "book_comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id", nullable = false, unique = true)
    private long commentId;

    @Column(name = "book_id", nullable = false)
    private long bookId;

    @Column(name = "comment", nullable = false)
    private String comment;

    @Column(name = "created", nullable = false)
    private Date created;

    public Comment(long bookId, String comment) {
        this.bookId = bookId;
        this.comment = comment;
        this.created = new Date();
    }
}
