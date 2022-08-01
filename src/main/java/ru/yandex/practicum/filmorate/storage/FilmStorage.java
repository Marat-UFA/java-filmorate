package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface FilmStorage {

    Film get (int FilmId);
    Film save (Film film);
    Film update (Film film);
    void addLike(Film film, User user);
    void deleteLike(Film film, User user);
    Collection<Film> getAllFilm();
    Collection<Film> getFilmPopularWithCount(int count);
    Collection<Film> getFilmPopular();
}
