package ru.otus.spring.hw.application.business.repository.withEvents;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import ru.otus.spring.hw.application.model.Author;
import ru.otus.spring.hw.application.model.Book;
import ru.otus.spring.hw.application.model.Genre;
import ru.otus.spring.hw.application.model.Lang;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ControllerTest {

    @Autowired
    private RouterFunction route;

    @Test
    public void testAuthors() {
        WebTestClient client = WebTestClient
                .bindToRouterFunction(route)
                .build();

        client.get()
                .uri("/api/author")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Author.class)
                .hasSize(13);
    }

    @Test
    public void testGenres() {
        WebTestClient client = WebTestClient
                .bindToRouterFunction(route)
                .build();

        client.get()
                .uri("/api/genre")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Genre.class)
                .hasSize(3);
    }

    @Test
    public void testBooks() {
        WebTestClient client = WebTestClient
                .bindToRouterFunction(route)
                .build();

        client.get()
                .uri("/api/book")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Book.class)
                .hasSize(10);
    }

    @Test
    public void testEditBook() {
        WebTestClient client = WebTestClient
                .bindToRouterFunction(route)
                .build();

        WebTestClient.ResponseSpec responseSpec = client.get()
                .uri("/api/book/")
                .exchange();
        FluxExchangeResult<Book> result = responseSpec.returnResult(Book.class);
        result.getResponseBody().collectList().subscribe(books -> {
            Book book = books.get(9);
            book.setName("Design Patterns: Elements of Reusable Object-Oriented Software");
            book.getAuthors().remove(3);
            book.getAuthors().remove(2);
            client.put()
                    .uri("/api/book/")
                    .body(BodyInserters.fromValue(book))
                    .exchange()
                    .expectStatus()
                    .isOk()
                    .expectBody(Book.class)
                    .isEqualTo(book);
        });
    }

    @Test
    public void testCreateBook() {
        WebTestClient client = WebTestClient
                .bindToRouterFunction(route)
                .build();

        Lang lang = client.get()
                .uri("/api/lang")
                .exchange().returnResult(Lang.class).getResponseBody().blockFirst();
        Genre genre = client.get()
                .uri("/api/genre")
                .exchange().returnResult(Genre.class).getResponseBody().blockFirst();
        Author author = client.get()
                .uri("/api/author")
                .exchange().returnResult(Author.class).getResponseBody().blockFirst();
        Book book = new Book("lalala new book", genre, lang, author);
        client.post()
                .uri("/api/book/")
                .body(BodyInserters.fromValue(book))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Book.class);
        Book saved = client.get()
                .uri("/api/book/")
                .exchange()
                .returnResult(Book.class).getResponseBody().blockLast();
        assertThat(saved.getName()).isEqualTo(book.getName());
        assertThat(saved.getAuthors().size()).isEqualTo(1);
        assertThat(saved.getAuthors().get(0).getName()).isEqualTo(author.getName());
        assertThat(saved.getGenre().getName()).isEqualTo(genre.getName());
        assertThat(saved.getLang().getName()).isEqualTo(lang.getName());
    }

    @Test
    public void testDeleteBook() {
        WebTestClient client = WebTestClient
                .bindToRouterFunction(route)
                .build();

        WebTestClient.ResponseSpec responseSpec = client.get()
                .uri("/api/book/")
                .exchange();
        FluxExchangeResult<Book> result = responseSpec.returnResult(Book.class);
        result.getResponseBody().collectList().subscribe(books -> {
            Book book = books.get(0);
            client.get()
                    .uri("/api/book/" + book.getBookId())
                    .exchange()
                    .expectStatus()
                    .isOk()
                    .expectBody(Book.class)
                    .value(book1 -> assertThat(book1).isNotNull());
            client.delete()
                    .uri("/api/book/" + book.getBookId())
                    .exchange()
                    .expectStatus()
                    .isOk();
            client.get()
                    .uri("/api/book/" + book.getBookId())
                    .exchange()
                    .expectStatus()
                    .isOk()
                    .expectBody(Book.class)
                    .value(book1 -> assertThat(book1).isNull());
        });
    }

    @Test
    public void tessLangs() {
        WebTestClient client = WebTestClient
                .bindToRouterFunction(route)
                .build();

        client.get()
                .uri("/api/lang")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Lang.class)
                .hasSize(3);
    }

}
