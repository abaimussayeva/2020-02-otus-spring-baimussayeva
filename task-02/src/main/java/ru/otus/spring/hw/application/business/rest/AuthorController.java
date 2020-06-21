package ru.otus.spring.hw.application.business.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.hw.domain.business.dto.AuthorDto;
import ru.otus.spring.hw.domain.business.services.AuthorService;

import java.util.Collection;

@RestController
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/api/author")
    Collection<AuthorDto> authors() {
        return authorService.getAuthors();
    }
}
