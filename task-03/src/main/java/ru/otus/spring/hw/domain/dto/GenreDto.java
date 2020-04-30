package ru.otus.spring.hw.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenreDto implements HasName {
    private String genreId;
    private String name;
    private String parentId;
    private List<GenreDto> childGenres;

    public GenreDto(String genreId, String name, String parentId) {
        this.genreId = genreId;
        this.name = name;
        this.parentId = parentId;
        this.childGenres = new ArrayList<>();
    }

    public GenreDto(String genreId, String name) {
        this.genreId = genreId;
        this.name = name;
        this.childGenres = new ArrayList<>();
    }

}
