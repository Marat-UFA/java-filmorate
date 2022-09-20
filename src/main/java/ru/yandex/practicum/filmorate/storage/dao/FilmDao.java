package ru.yandex.practicum.filmorate.storage.dao;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmDao {
    Film saveFilm(Film film);
    Film getFilmById(int id);
    void deleteFilmById(int id);
    Film updateFilm(Film film);
    Collection<Film> getFilmPopular(int count);
    Collection<Film> getAllFilms();
}
