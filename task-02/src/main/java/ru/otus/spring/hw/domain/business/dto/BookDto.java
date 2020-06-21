package ru.otus.spring.hw.domain.business.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookDto {
    private long bookId;
    private String name;
    private GenreDto genre;
    private LangDto lang;
    private List<AuthorDto> authors;
}
