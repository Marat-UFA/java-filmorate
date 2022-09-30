package ru.yandex.practicum.filmorate.storage.dao;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

public interface GenreDao {
    void saveGenres(Film film);
    List<Genre> getAllGenresByIdWithFilm(int id);
    void deleteGenresInFilm(int id);
    Genre getGenreById(int id);
    List<Genre> getAllGenres();
}
