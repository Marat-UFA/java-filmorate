package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.comparison.TreeComparison;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

@Component
public class InMemoryFilmStorage implements FilmStorage {

    int id = 0;
    HashMap<Integer, Film> filmMap = new HashMap<>();

    private final TreeSet filmTreeSet = new TreeSet(new TreeComparison());

    @Override
    public Film get(int FilmId) {
        return filmMap.get(FilmId);
    }

    @Override
    public Film save(Film film) {
        film.setId(++id);
        filmMap.put(film.getId(), film);
        filmTreeSet.add(film);
        return film;
    }

    @Override
    public Film update(Film film) {
        filmMap.remove(film.getId(), film);
        filmMap.put(film.getId(), film);
        filmTreeSet.remove(film);
        filmTreeSet.add(film);
        return film;
    }

    @Override
    public void addLike(Film film, User user) {
        filmTreeSet.remove(film);
        film.getUserID().add(user.getId());

        filmTreeSet.add(film);
    }

    @Override
    public void deleteLike(Film film, User user) {
        filmTreeSet.remove(film);
        film.getUserID().remove(user.getId());

        filmTreeSet.add(film);
    }

    @Override
    public Collection<Film> getAllFilm() {
        return filmMap.values();
    }

    @Override
    public Collection<Film> getFilmPopularWithCount(int count) {
            List<Film> filmList = new ArrayList<>();
            List<Film> filmListWithFilmTreeSet = new ArrayList<>(filmTreeSet);
            for (int i = 0; i < count; i++) {
                filmList.add(filmListWithFilmTreeSet.get(i));
            }
            return filmList;
    }

    @Override
    public Collection<Film> getFilmPopular() {
        if (filmTreeSet.size() < 10) {
            return new ArrayList<Film>(filmTreeSet);
        } else {
            List<Film> filmList = new ArrayList<>();
            List<Film> filmListWithFilmTreeSet = new ArrayList<>(filmTreeSet);
            for (int i = 0; i < 10; i++) {
                filmList.add(filmListWithFilmTreeSet.get(i));
            }
            return filmList;
        }
    }
}
