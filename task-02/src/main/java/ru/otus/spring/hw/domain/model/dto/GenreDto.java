package ru.otus.spring.hw.domain.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenreDto {
    private long genreId;
    private String name;
    private Long parentId;
    private List<GenreDto> childGenres;

    public GenreDto(long genreId, String name, Long parentId) {
        this.genreId = genreId;
        this.name = name;
        this.parentId = parentId;
        this.childGenres = new ArrayList<>();
    }

    public GenreDto(long genreId, String name) {
        this.genreId = genreId;
        this.name = name;
        this.childGenres = new ArrayList<>();
    }

}
