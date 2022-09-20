package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.Collection;
import java.util.List;

public interface FilmStorage {

    Film get (int FilmId);
    Film save (Film film);
    void deleteFilm (int id);
    Film update (Film film);
    void addLike(int filmId, int userId);
    void deleteLike(int filmId, int userId);
    Collection<Film> getAllFilm();
    Collection<Film> getFilmPopularWithCount(int count);
    Collection<Film> getFilmPopular(int count);
    Genre getGenreById(int id);
    List<Genre> getAllGenres();
    boolean checkingFilm(int id);
    Mpa getMpaById(int id);
    List<Mpa> getAllMpa();

}
