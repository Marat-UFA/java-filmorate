package ru.yandex.practicum.filmorate.storage.dao;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

public interface GenreDao {
    void saveGenres(Film film);
    List<Genre> getAllGenresByIdWithFilm(Integer id);
    void deleteGenresInFilm(Integer id);
    Genre getGenreById(Integer id);
    List<Genre> getAllGenres();
}
