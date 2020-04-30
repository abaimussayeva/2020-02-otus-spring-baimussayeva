package ru.otus.spring.hw.application.events;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;
import ru.otus.spring.hw.application.business.repository.AuthorRepository;
import ru.otus.spring.hw.application.business.repository.GenreRepository;
import ru.otus.spring.hw.application.business.repository.LangRepository;
import ru.otus.spring.hw.application.model.Book;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class MongoBookSaveEventsListener extends AbstractMongoEventListener<Book> {

    private final GenreRepository genreRepo;
    private final AuthorRepository authorRepo;
    private final LangRepository langRepo;

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Book> event) {
        super.onBeforeConvert(event);
        Book student = event.getSource();
        if (student.getAuthors() != null) {
            student.getAuthors().stream().filter(e -> Objects.isNull(e.getAuthorId())).forEach(authorRepo::save);
        }
        if (Objects.isNull(student.getGenre().getGenreId())) {
            genreRepo.save(student.getGenre());
        }
        if (Objects.isNull(student.getLang().getLangId())) {
            langRepo.save(student.getLang());
        }
    }
}
