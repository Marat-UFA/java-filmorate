package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.Exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.DbFilmStorage;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;
import java.util.List;

@Service
public class FilmService {

    @Autowired
    UserStorage userStorage;

    @Autowired
    FilmStorage filmStorage;


    public void addLike(int filmId, int userId) {
        if (filmId <= 0) {
            throw new NotFoundException("Film Id < 0");
        }
        if (userId <= 0) {
            throw new NotFoundException("User Id < 0");
        }
        if (!filmStorage.checkingFilm(filmId)) {
            throw new NotFoundException("Film with id=" + filmId + " not found");
        }
        if (!userStorage.checkingUser(userId)) {
            throw new NotFoundException("User with id=" + userId + " not found");
        }
        filmStorage.addLike(filmId, userId);
    }

    public void deleteLike(int filmId, int userId) {
        if (filmId <= 0) {
            throw new NotFoundException("Film Id < 0");
        }
        if (userId <= 0) {
            throw new NotFoundException("User Id < 0");
        }
        if (!filmStorage.checkingFilm(filmId)) {
            throw new NotFoundException("Film with id=" + filmId + " not found");
        }
        if (!userStorage.checkingUser(userId)) {
            throw new NotFoundException("User with id=" + userId + " not found");
        }
        filmStorage.deleteLike(filmId, userId);
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
        if (filmId <= 0) {
            throw new NotFoundException("Film id < 0");
        }

        final Film film = filmStorage.get(filmId);

        if (film == null) {
            throw new NotFoundException("Film with id=" + filmId + " not found");
        }

        return film;
    }

    public void deleteFilm(int filmId) {
        if (filmId <= 0) {
            throw new NotFoundException("Film id < 0");
        }
        filmStorage.deleteFilm(filmId);
    }

    public Collection<Film> getFilmPopularWithCount(int count) {
        if (count <= 0) {
            throw new NotFoundException("Film with count <= 0");
        }
        return filmStorage.getFilmPopularWithCount(count);
    }

    public Collection<Film> getFilmPopular(int count) {
        return filmStorage.getFilmPopular(count);
    }

    public Genre getGenreById(int id) {
        if (id <= 0) {
            throw new NotFoundException("Genre Id < 0");
        }
        return filmStorage.getGenreById(id);
    }

    public Genre getAllGenreById(int id) {
        if (id <= 0) {
            throw new NotFoundException("Genre Id < 0");
        }
        return filmStorage.getGenreById(id);
    }

    public List<Genre> getAllGenreByIdFilm(int id) {
        if (id <= 0) {
            throw new NotFoundException("Film Id < 0");
        }
        return filmStorage.getGenresByIdFilm(id);
    }

    public List<Genre> getAllGenres() {
        return filmStorage.getAllGenres();
    }


    public Mpa getMpaById(int id) {
        if (id <= 0) {
            throw new NotFoundException("Mpa Id < 0");
        }
        return filmStorage.getMpaById(id);
    }

    public List<Mpa> getAllMpa() {
        return filmStorage.getAllMpa();
    }
}


