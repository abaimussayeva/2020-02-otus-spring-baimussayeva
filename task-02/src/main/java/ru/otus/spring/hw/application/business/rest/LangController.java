package ru.otus.spring.hw.application.business.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.hw.domain.business.dto.LangDto;
import ru.otus.spring.hw.domain.business.services.LangService;

import java.util.Collection;

@RestController
public class LangController {

    private final LangService langService;

    public LangController(LangService langService) {
        this.langService = langService;
    }

    @GetMapping("/api/lang")
    Collection<LangDto> langs() {
        return langService.getLangs();
    }
}
