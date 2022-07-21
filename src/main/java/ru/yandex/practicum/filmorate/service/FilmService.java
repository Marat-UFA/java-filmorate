package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.Exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.util.Collection;

@Service
public class FilmService {

    @Autowired
    InMemoryUserStorage userStorage;

    @Autowired
    InMemoryFilmStorage filmStorage;


    public void addLike(int filmId, int userId) {
        final Film film = filmStorage.get(filmId);
        final User user = userStorage.get(userId);

        if (film == null) {
            throw new NotFoundException("Film with id=" + userId + " not found");
        }
        if (user == null) {
            throw new NotFoundException("User with id=" + user + " not found");
        }
        filmStorage.addLike(film, user);
    }

    public void deleteLike(int filmId, int userId) {
        final Film film = filmStorage.get(filmId);
        final User user = userStorage.get(userId);

        if (film == null) {
            throw new NotFoundException("Film with id=" + userId + " not found");
        }
        if (user == null) {
            throw new NotFoundException("User with id=" + user + " not found");
        }
        filmStorage.deleteLike(film, user);
    }

    public Collection<Film> getAllFilm() {
        return filmStorage.getAllFilm();
    }

    public Film save(Film film) {
        return filmStorage.save(film);
    }

    public Film update(Film film) {
        if (film.getId() <= 0) {
            throw new NotFoundException("Film Id < 0");
        }
        return filmStorage.update(film);
    }

    public Film getFilm(int filmId) {
        final Film film = filmStorage.get(filmId);
        if (film == null) {
            throw new NotFoundException("Film with id=" + filmId + " not found");
        }
        if (filmId <= 0) {
            throw new NotFoundException("Film id < 0");
        }
        return film;
    }

    public Collection<Film> getFilmPopularWithCount(int count) {
        if (count <= 0) {
            throw new NotFoundException("Film with count <= 0");
        }
        return filmStorage.getFilmPopularWithCount(count);
    }

    public Collection<Film> getFilmPopular() {
        return filmStorage.getFilmPopular();
    }
}
