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
public class Genre implements HasName {
    @Id
    private String genreId;

    private String name;

    private String parentId;

    public Genre(String id, String name) {
        this.genreId = id;
        this.name = name;
    }

    public Genre setName(String name) {
        this.name = name;
        return this;
    }

    public Genre setParentId(String parentId) {
        this.parentId = parentId;
        return this;
    }
}
