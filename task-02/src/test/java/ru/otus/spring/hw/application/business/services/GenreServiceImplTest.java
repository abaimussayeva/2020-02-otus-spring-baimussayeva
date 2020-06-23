package ru.otus.spring.hw.application.business.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring.hw.application.business.repository.GenreRepository;
import ru.otus.spring.hw.domain.business.dto.GenreDto;
import ru.otus.spring.hw.domain.business.services.GenreService;
import ru.otus.spring.hw.domain.errors.DBOperationException;
import ru.otus.spring.hw.domain.model.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Service для работы с жанрами должен ")
class GenreServiceImplTest {

    @DisplayName(" вернуть дерево жанров")
    @Test
    void getAllTree(@Mock GenreRepository genreDao) throws DBOperationException {
        GenreService genreService = new GenreServiceImpl(genreDao);
        when(genreDao.findAll()).thenReturn(List.of(new Genre(1L, "Худ. лит-ра", null),
                new Genre(2L, "Проза", 1L),
                new Genre(3L, "Поэзия", 1L),
                new Genre(4L, "Повести, рассказы", 1L),
                new Genre(5L, "Биография. Мемурары", 1L),
                new Genre(10L, "Фантастика", 1L),
                new Genre(11L, "Исторический роман", 1L),
                new Genre(6L, "Наука", null),
                new Genre(7L, "Естественные науки", 6L),
                new Genre(8L, "Физика", 7L),
                new Genre(9L, "Физика", 7L),
                new Genre(12L, "IT", null),
                new Genre(13L, "Языки программирования", 12L),
                new Genre(14L, "Java", 13L),
                new Genre(15L, "Дизайн программного обеспечения", 13L)
                ));
        List<GenreDto> genres = genreService.getGenres();
        assertThat(genres).hasSize(3)
                .allMatch(g -> !g.getName().isEmpty())
                .allMatch(g -> !g.getChildGenres().isEmpty());
        assertThat(genres.get(0).getChildGenres()).hasSize(6);
        assertThat(genres.get(1).getChildGenres().get(0).getChildGenres()).hasSize(2);
        assertThat(genres.get(2).getChildGenres().get(0).getChildGenres()).hasSize(2);
    }

}