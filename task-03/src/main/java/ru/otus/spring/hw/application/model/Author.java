package ru.otus.spring.hw.application.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import ru.otus.spring.hw.domain.dto.HasName;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Author implements HasName {
    @Id
    private String authorId;

    private String name;

    private String description;

    public Author(String name) {
        this.name = name;
    }
}
