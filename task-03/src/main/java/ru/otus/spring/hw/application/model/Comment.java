package ru.otus.spring.hw.application.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Comment {
    @Id
    private String commentId;

    private String bookId;

    private String comment;

    private Date created;

    public Comment(String bookId, String comment) {
        this.bookId = bookId;
        this.comment = comment;
        this.created = new Date();
    }
}
