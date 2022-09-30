package ru.yandex.practicum.filmorate.storage.dao;

import ru.yandex.practicum.filmorate.model.Film;
import java.util.Set;

public interface GenresListDao {
    void saveGenres(Film film);
    Set<Integer> getAllGenresByIdWithFilm(int filmId);
    void deleteGenresInFilm(int id);
}
