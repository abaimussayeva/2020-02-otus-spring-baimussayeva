package ru.otus.spring.hw.application.business.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.otus.spring.hw.domain.business.dto.AuthorDto;
import ru.otus.spring.hw.domain.model.Author;

import java.util.Objects;

@Component
public class AuthorMapper {

    private final ModelMapper mapper;

    public AuthorMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public Author toEntity(AuthorDto dto) {
        return Objects.isNull(dto) ? null : mapper.map(dto, Author.class);
    }

    public AuthorDto toDto(Author entity) {
        return Objects.isNull(entity) ? null : mapper.map(entity, AuthorDto.class);
    }
}
