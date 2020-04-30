package ru.otus.spring.hw.application.business.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.hw.application.model.Lang;

public interface LangRepository extends MongoRepository<Lang, String> {

}
