package ru.otus.spring.hw.application.business.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.otus.spring.hw.domain.business.dto.LangDto;
import ru.otus.spring.hw.domain.model.Lang;

import java.util.Objects;

@Component
public class LangMapper {
    private final ModelMapper mapper;

    public LangMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public Lang toEntity(LangDto dto) {
        return Objects.isNull(dto) ? null : mapper.map(dto, Lang.class);
    }

    public LangDto toDto(Lang entity) {
        return Objects.isNull(entity) ? null : mapper.map(entity, LangDto.class);
    }
}
