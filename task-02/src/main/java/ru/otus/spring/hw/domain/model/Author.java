package ru.otus.spring.hw.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "author_id", nullable = false, unique = true)
    private long authorId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;
}
