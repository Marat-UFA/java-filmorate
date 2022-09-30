package ru.yandex.practicum.filmorate.storage.dao;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmDao {
    Film saveFilm(Film film);
    Film getFilmById(int id);
    void deleteFilmById(int id);
    Film updateFilm(Film film);
    List<Film> getFilmPopular(int count);
    List<Film> getAllFilms();
}
