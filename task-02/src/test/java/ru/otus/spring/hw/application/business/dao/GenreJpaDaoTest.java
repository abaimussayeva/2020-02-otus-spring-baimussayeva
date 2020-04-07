package ru.otus.spring.hw.application.business.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import ru.otus.spring.hw.domain.business.dao.AuthorDao;
import ru.otus.spring.hw.domain.business.dao.BookDao;
import ru.otus.spring.hw.domain.business.dao.LangDao;
import ru.otus.spring.hw.domain.model.Genre;
import ru.otus.spring.hw.domain.model.dto.GenreDto;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Jpa для работы с жанрами должен ")
@DataJpaTest
@Import({GenreJpaDao.class})
class GenreJpaDaoTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private GenreJpaDao dao;

    @MockBean
    private BookDao bookDao;

    @MockBean
    private AuthorDao authorDao;

    @MockBean
    private LangDao langDao;

    @DisplayName(" вернуть один жанр по ид с дочерними жанрами")
    @Test
    void getById() {
        Genre genre = dao.getById(1L).orElseThrow();
        assertThat(genre.getName()).isEqualTo("Художественная литература");
        assertThat(genre.getChildGenres()).hasSize(6)
                .allMatch(g -> !g.getName().isEmpty())
                .allMatch(g -> g.getParentId() == genre.getGenreId());
    }

    @DisplayName(" вернуть дерево жанров")
    @Test
    void getAllTree() {
        List<GenreDto> genres = dao.getAllTree();
        assertThat(genres).hasSize(3)
                .allMatch(g -> !g.getName().isEmpty())
                .allMatch(g -> !g.getChildGenres().isEmpty());
        assertThat(genres.get(0).getChildGenres()).hasSize(6);
        assertThat(genres.get(1).getChildGenres().get(0).getChildGenres()).hasSize(2);
        assertThat(genres.get(2).getChildGenres().get(0).getChildGenres()).hasSize(2);
    }

    @DisplayName(" вернуть список жанров без иерархии")
    @Test
    void getAllFlat() {
        List<GenreDto> genres = dao.getAllFlat();
        assertThat(genres).hasSize(15)
                .allMatch(g -> !g.getName().isEmpty());
    }
}