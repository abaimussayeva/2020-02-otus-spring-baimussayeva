package ru.otus.spring.hw.application.business.services;

import org.springframework.stereotype.Service;
import ru.otus.spring.hw.application.business.repository.GenreRepository;
import ru.otus.spring.hw.domain.business.services.GenreService;
import ru.otus.spring.hw.domain.errors.DBOperationException;
import ru.otus.spring.hw.domain.model.Genre;
import ru.otus.spring.hw.domain.model.dto.GenreDto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    public GenreServiceImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    public List<GenreDto> getGenres() throws DBOperationException {
        try {
            List<Genre> genres = genreRepository.findAll();
            if (genres.isEmpty()) {
                return Collections.emptyList();
            }
            List<GenreDto> genreDtos = new ArrayList<>();
            genres.forEach(genre -> genreDtos.add(new GenreDto(genre.getGenreId(), genre.getName(), genre.getParentId())));
            Map<Long, GenreDto> genreMap = genreDtos.stream().collect(Collectors.toMap(GenreDto::getGenreId, g -> g));
            for (GenreDto genre: genreDtos) {
                if (genre.getParentId() != null) {
                    genreMap.get(genre.getParentId()).getChildGenres().add(genre);
                }
            }
            return genreDtos.stream().filter(g -> g.getParentId() == null).collect(Collectors.toList());
        } catch (Exception e) {
            throw new DBOperationException("Genres get error", e);
        }
    }
}
