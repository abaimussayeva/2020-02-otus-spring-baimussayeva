package ru.otus.spring.hw.application.events;

import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterDeleteEvent;
import org.springframework.stereotype.Component;
import ru.otus.spring.hw.application.business.repository.CommentRepository;
import ru.otus.spring.hw.application.model.Book;
import ru.otus.spring.hw.application.model.Comment;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MongoBookCascadeDeleteEventsListener extends AbstractMongoEventListener<Book> {

    private final CommentRepository commentRepository;

    @Override
    public void onAfterDelete(AfterDeleteEvent<Book> event) {
        super.onAfterDelete(event);
        Document source = event.getSource();
        String id = source.get("_id").toString();
        List<Comment> comments = commentRepository.findByBookIdOrderByCreatedDesc(id);
        commentRepository.deleteAll(comments);
    }
}
