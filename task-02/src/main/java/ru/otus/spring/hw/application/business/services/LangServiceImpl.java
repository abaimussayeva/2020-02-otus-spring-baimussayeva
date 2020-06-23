package ru.otus.spring.hw.application.business.services;

import org.springframework.stereotype.Service;
import ru.otus.spring.hw.application.business.mapper.LangMapper;
import ru.otus.spring.hw.application.business.repository.LangRepository;
import ru.otus.spring.hw.domain.business.dto.LangDto;
import ru.otus.spring.hw.domain.business.services.LangService;
import ru.otus.spring.hw.domain.errors.DBOperationException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LangServiceImpl implements LangService {

    private final LangRepository langRepository;
    private final LangMapper langMapper;

    public LangServiceImpl(LangRepository langRepository, LangMapper langMapper) {
        this.langRepository = langRepository;
        this.langMapper = langMapper;
    }

    @Override
    public List<LangDto> getLangs() throws DBOperationException {
        try {
            return langRepository.findAll()
                    .stream()
                    .map(langMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new DBOperationException("Languages get error", e);
        }
    }
}
