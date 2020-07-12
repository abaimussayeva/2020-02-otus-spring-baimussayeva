package ru.otus.spring.hw.application.events;

import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterDeleteEvent;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import ru.otus.spring.hw.application.business.repository.CommentRepository;
import ru.otus.spring.hw.application.model.Book;
import ru.otus.spring.hw.application.model.Comment;

@Component
@RequiredArgsConstructor
public class MongoBookCascadeDeleteEventsListener extends AbstractMongoEventListener<Book> {

    private final CommentRepository commentRepository;

    @Override
    public void onAfterDelete(AfterDeleteEvent<Book> event) {
        super.onAfterDelete(event);
        Document source = event.getSource();
        String id = source.get("bookId").toString();
        Flux<Comment> comments = commentRepository.findByBookIdOrderByCreatedDesc(id);
        comments.collectList().subscribe(comment -> commentRepository.deleteAll(comment).subscribe());
    }
}
