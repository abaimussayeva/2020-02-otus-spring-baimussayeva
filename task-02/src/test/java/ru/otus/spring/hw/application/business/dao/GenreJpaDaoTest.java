package ru.otus.spring.hw.application.business.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import ru.otus.spring.hw.domain.business.dao.AuthorDao;
import ru.otus.spring.hw.domain.business.dao.BookDao;
import ru.otus.spring.hw.domain.business.dao.LangDao;
import ru.otus.spring.hw.domain.model.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Jpa для работы с жанрами должен ")
@DataJpaTest
@Import({GenreJpaDao.class})
class GenreJpaDaoTest {

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
    }

    @DisplayName(" вернуть список жанров без иерархии")
    @Test
    void getAllFlat() {
        List<Genre> genres = dao.getAll();
        assertThat(genres).hasSize(15)
                .allMatch(g -> !g.getName().isEmpty());
    }
}