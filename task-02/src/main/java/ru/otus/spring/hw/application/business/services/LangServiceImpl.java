package ru.otus.spring.hw.application.business.services;

import org.springframework.stereotype.Service;
import ru.otus.spring.hw.domain.business.dao.LangDao;
import ru.otus.spring.hw.domain.business.services.LangService;
import ru.otus.spring.hw.domain.errors.DBOperationException;
import ru.otus.spring.hw.domain.model.Lang;

import java.util.List;

@Service
public class LangServiceImpl implements LangService {

    private final LangDao langDao;

    public LangServiceImpl(LangDao langDao) {
        this.langDao = langDao;
    }

    @Override
    public List<Lang> getLangs() throws DBOperationException {
        try {
            return langDao.getAll();
        } catch (Exception e) {
            throw new DBOperationException("Languages get error", e);
        }
    }
}
