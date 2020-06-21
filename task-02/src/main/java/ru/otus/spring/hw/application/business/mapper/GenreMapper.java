package ru.otus.spring.hw.application.business.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.otus.spring.hw.domain.business.dto.GenreDto;
import ru.otus.spring.hw.domain.model.Author;
import ru.otus.spring.hw.domain.model.Genre;

import java.util.Objects;

@Component
public class GenreMapper {

    private final ModelMapper mapper;

    public GenreMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public Genre toEntity(GenreDto dto) {
        return Objects.isNull(dto) ? null : mapper.map(dto, Genre.class);
    }

    public GenreDto toDto(Author entity) {
        return Objects.isNull(entity) ? null : mapper.map(entity, GenreDto.class);
    }
}
