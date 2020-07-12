package ru.otus.spring.hw.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.otus.spring.hw.application.business.repository.AuthorRepository;
import ru.otus.spring.hw.application.business.repository.BookRepository;
import ru.otus.spring.hw.application.business.repository.GenreRepository;
import ru.otus.spring.hw.application.business.repository.LangRepository;
import ru.otus.spring.hw.application.config.AppProps;
import ru.otus.spring.hw.application.model.Author;
import ru.otus.spring.hw.application.model.Book;
import ru.otus.spring.hw.application.model.Lang;
import ru.otus.spring.hw.domain.dto.GenreDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromValue;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@SpringBootApplication
@EnableConfigurationProperties({AppProps.class})
public class LibraryApp {

    public static void main(String[] args) {
        SpringApplication.run(LibraryApp.class, args);
    }

    @Bean
    public RouterFunction<ServerResponse> composedRoutes(BookRepository bookRepository,
                                                         AuthorRepository authorRepository,
                                                         GenreRepository genreRepository,
                                                         LangRepository langRepository) {
        return route()
                .GET("/api/author", accept(APPLICATION_JSON),
                        serverRequest -> ok().contentType(APPLICATION_JSON)
                                .body(authorRepository.findAll(), Author.class))
                .GET("/api/lang", accept(APPLICATION_JSON),
                        serverRequest -> ok().contentType(APPLICATION_JSON)
                                .body(langRepository.findAll(), Lang.class))
                .GET("/api/genre", accept(APPLICATION_JSON),
                        serverRequest -> ok().contentType(APPLICATION_JSON)
                                .body(genreRepository.findAll().collectList().flatMap(genres -> {
                                    List<GenreDto> genreDtos = new ArrayList<>();
                                    genres.forEach(genre -> genreDtos.add(new GenreDto(genre.getGenreId(), genre.getName(), genre.getParentId())));
                                    Map<String, GenreDto> genreMap = genreDtos.stream().collect(Collectors.toMap(GenreDto::getGenreId, g -> g));
                                    for (GenreDto genre : genreDtos) {
                                        if (genre.getParentId() != null) {
                                            genreMap.get(genre.getParentId()).getChildGenres().add(genre);
                                        }
                                    }
                                    return Mono.just(genreDtos.stream().filter(g -> g.getParentId() == null).collect(Collectors.toList()));
                                }).single(), GenreDto.class))
                .GET("/api/book", accept(APPLICATION_JSON),
                        serverRequest -> ok().contentType(APPLICATION_JSON)
                                .body(bookRepository.findAll(), Book.class))
                .GET("/api/book/{id}", accept(APPLICATION_JSON),
                        request -> bookRepository.findById(request.pathVariable("id"))
                                .flatMap(book -> ok().contentType(APPLICATION_JSON).body(fromValue(book)))
                )
                .POST("/api/book",
                        request -> request.bodyToMono(Book.class)
                                .map(bookRepository::insert)
                                .flatMap(Mono::single)
                                .flatMap(book -> ok().contentType(APPLICATION_JSON).body(fromValue(book)))
                )
                .PUT("/api/book", accept(APPLICATION_JSON),
                        request -> request.bodyToMono(Book.class)
                                .map(bookRepository::save)
                                .flatMap(Mono::single)
                                .flatMap(book -> ok().contentType(APPLICATION_JSON).body(fromValue(book)))
                )
                .DELETE("/api/book/{id}", accept(APPLICATION_JSON),
                        request -> bookRepository.deleteById(request.pathVariable("id"))
                                .flatMap(v -> ok().contentType(APPLICATION_JSON).body(fromValue(v)))
                )
                .build();
    }
}
