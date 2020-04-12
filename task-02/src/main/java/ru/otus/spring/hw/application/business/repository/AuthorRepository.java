package ru.otus.spring.hw.application.business.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.hw.domain.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {

}
