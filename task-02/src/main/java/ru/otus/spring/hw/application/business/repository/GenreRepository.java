package ru.otus.spring.hw.application.business.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.hw.domain.model.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {

}
