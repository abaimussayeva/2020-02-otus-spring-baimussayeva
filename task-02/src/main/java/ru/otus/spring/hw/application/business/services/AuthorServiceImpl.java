package ru.otus.spring.hw.application.business.services;

import org.springframework.stereotype.Service;
import ru.otus.spring.hw.application.business.mapper.AuthorMapper;
import ru.otus.spring.hw.application.business.repository.AuthorRepository;
import ru.otus.spring.hw.domain.business.dto.AuthorDto;
import ru.otus.spring.hw.domain.business.services.AuthorService;
import ru.otus.spring.hw.domain.errors.DBOperationException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    public AuthorServiceImpl(AuthorRepository authorRepository, AuthorMapper authorMapper) {
        this.authorRepository = authorRepository;
        this.authorMapper = authorMapper;
    }

    @Override
    public List<AuthorDto> getAuthors() throws DBOperationException {
        try {
            return authorRepository.findAll()
                    .stream()
                    .map(authorMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new DBOperationException("Authors get error", e);
        }
    }
}
