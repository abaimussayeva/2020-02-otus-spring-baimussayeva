package ru.otus.spring.hw.application.business.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.hw.domain.model.Lang;

public interface LangRepository extends JpaRepository<Lang, Long> {

}
