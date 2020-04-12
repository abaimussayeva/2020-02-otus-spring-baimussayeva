package ru.otus.spring.hw.application.business.services;

import org.springframework.stereotype.Service;
import ru.otus.spring.hw.application.business.repository.LangRepository;
import ru.otus.spring.hw.domain.business.services.LangService;
import ru.otus.spring.hw.domain.errors.DBOperationException;
import ru.otus.spring.hw.domain.model.Lang;

import java.util.List;

@Service
public class LangServiceImpl implements LangService {

    private final LangRepository langRepository;

    public LangServiceImpl(LangRepository langRepository) {
        this.langRepository = langRepository;
    }

    @Override
    public List<Lang> getLangs() throws DBOperationException {
        try {
            return langRepository.findAll();
        } catch (Exception e) {
            throw new DBOperationException("Languages get error", e);
        }
    }
}
