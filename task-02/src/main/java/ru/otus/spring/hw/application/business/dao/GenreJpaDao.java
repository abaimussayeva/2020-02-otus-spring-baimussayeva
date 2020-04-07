package ru.otus.spring.hw.application.business.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.hw.domain.business.dao.GenreDao;
import ru.otus.spring.hw.domain.model.Genre;
import ru.otus.spring.hw.domain.model.dto.GenreDto;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;
import java.util.stream.Collectors;

@Transactional
@Repository
public class GenreJpaDao implements GenreDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<Genre> getById(long id) {
        return Optional.ofNullable(em.find(Genre.class, id));
    }

    @Override
    public List<GenreDto> getAllFlat() {
        List<Genre> genres = em.createQuery("select g from Genre g", Genre.class).getResultList();
        List<GenreDto> genreDtos = new ArrayList<>();
        genres.forEach(genre -> genreDtos.add(new GenreDto(genre.getGenreId(), genre.getName(), genre.getParentId())));
        return genreDtos;
    }

    @Override
    public List<GenreDto> getAllTree() {
        List<GenreDto> genres = getAllFlat();
        if (genres.isEmpty()) {
            return Collections.emptyList();
        }
        Map<Long, GenreDto> genreMap = genres.stream().collect(Collectors.toMap(GenreDto::getGenreId, g -> g));
        for (GenreDto genre: genres) {
            if (genre.getParentId() != null) {
                genreMap.get(genre.getParentId()).getChildGenres().add(genre);
            }
        }
        return genres.stream().filter(g -> g.getParentId() == null).collect(Collectors.toList());
    }
}
