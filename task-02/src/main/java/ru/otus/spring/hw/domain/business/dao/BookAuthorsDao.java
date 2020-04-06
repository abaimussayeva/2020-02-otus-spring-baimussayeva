package ru.otus.spring.hw.domain.business.dao;

import java.util.List;

public interface BookAuthorsDao {
    void deleteAuthors(long bookId);
    void insertAuthors(long bookId, List<Long>authors);
}
