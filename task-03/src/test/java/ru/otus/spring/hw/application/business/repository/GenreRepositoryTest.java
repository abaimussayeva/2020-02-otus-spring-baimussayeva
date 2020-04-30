package ru.otus.spring.hw.application.business.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.spring.hw.application.model.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Spring Data Mongo для работы с жанрами должен ")
class GenreRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private GenreRepository dao;

    @MockBean
    private BookRepository bookDao;

    @MockBean
    private AuthorRepository authorDao;

    @MockBean
    private LangRepository langDao;

    @DisplayName(" вернуть один жанр по ид с дочерними жанрами")
    @Test
    void getById() {
        Genre genre = dao.findAll().get(0);
        assertThat(genre.getName()).isEqualTo("Художественная литература");
    }

    @DisplayName(" вернуть список жанров без иерархии")
    @Test
    void getAllFlat() {
        List<Genre> genres = dao.findAll();
        assertThat(genres).hasSize(15)
                .allMatch(g -> !g.getName().isEmpty());
    }
}