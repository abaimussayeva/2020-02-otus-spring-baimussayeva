package ru.otus.spring.hw.application.business.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.hw.application.business.rest.error.NotFoundException;
import ru.otus.spring.hw.domain.business.dto.BookDto;
import ru.otus.spring.hw.domain.business.dto.Message;
import ru.otus.spring.hw.domain.business.services.BookService;
import ru.otus.spring.hw.domain.errors.DBOperationException;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;

@RestController
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/api/book")
    Collection<BookDto> books() {
        return bookService.getAllBooks();
    }

    @GetMapping("/api/book/{id}")
    BookDto getBook(@PathVariable Long id) {
        return bookService.getBookById(id).orElseThrow(NotFoundException::new);
    }

    @PostMapping("/api/book")
    ResponseEntity<BookDto> createBook(@Valid @RequestBody BookDto book) throws URISyntaxException {
        BookDto result = bookService.save(book);
        return ResponseEntity.created(new URI("/api/book/" + result.getBookId()))
                .body(result);
    }

    @PutMapping("/api/book")
    ResponseEntity<BookDto> updateBook(@Valid @RequestBody BookDto book) {
        BookDto result = bookService.save(book);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/api/book/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {
        bookService.removeBook(id);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(DBOperationException.class)
    public ResponseEntity<Message> handleDBOperation(DBOperationException ex) {
        return ResponseEntity.badRequest().body(new Message(ex.getMessage()));
    }
}
