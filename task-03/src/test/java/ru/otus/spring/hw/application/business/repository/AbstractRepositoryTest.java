package ru.otus.spring.hw.application.business.repository;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;

@DataMongoTest
@EnableConfigurationProperties
@ComponentScan({"ru.otus.spring.hw.application.config", "ru.otus.spring.hw.application.business.repository"})
public abstract class AbstractRepositoryTest {
}
