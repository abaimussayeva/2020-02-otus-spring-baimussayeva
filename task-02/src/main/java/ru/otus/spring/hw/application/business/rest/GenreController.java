package ru.otus.spring.hw.application.business.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.hw.domain.business.dto.GenreDto;
import ru.otus.spring.hw.domain.business.services.GenreService;

import java.util.Collection;

@RestController
public class GenreController {

    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping("/api/genre")
    Collection<GenreDto> genres() {
        return genreService.getGenres();
    }
}
