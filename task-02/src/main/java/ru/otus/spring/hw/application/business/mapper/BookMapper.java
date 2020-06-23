package ru.otus.spring.hw.application.business.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.otus.spring.hw.domain.business.dto.BookDto;
import ru.otus.spring.hw.domain.model.Book;

import java.util.Objects;

@Component
public class BookMapper {
    private final ModelMapper mapper;

    public BookMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public Book toEntity(BookDto dto) {
        return Objects.isNull(dto) ? null : mapper.map(dto, Book.class);
    }

    public BookDto toDto(Book entity) {
        return Objects.isNull(entity) ? null : mapper.map(entity, BookDto.class);
    }
}
