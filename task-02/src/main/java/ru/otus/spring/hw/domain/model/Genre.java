package ru.otus.spring.hw.domain.model;

import java.util.ArrayList;
import java.util.List;

public class Genre {
    private final long genreId;
    private final String name;
    private List<Genre> childGenres;

    public Genre(long genreId, String name) {
        this.genreId = genreId;
        this.name = name;
        this.childGenres = new ArrayList<>();
    }

    public long getGenreId() {
        return genreId;
    }

    public String getName() {
        return name;
    }

    public void addChild(Genre genre) {
        childGenres.add(genre);
    }

    public void addChildren(List<Genre> genres) {
        childGenres.addAll(genres);
    }

    public boolean hasChild() {
        return !childGenres.isEmpty();
    }

    public List<Genre> getChildren() {
        return new ArrayList<>(childGenres);
    }
}
