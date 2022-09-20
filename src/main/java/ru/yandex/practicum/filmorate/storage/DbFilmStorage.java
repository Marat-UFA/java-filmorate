package ru.yandex.practicum.filmorate.storage;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.impl.FilmImpl;
import ru.yandex.practicum.filmorate.storage.impl.GenreImpl;
import ru.yandex.practicum.filmorate.storage.impl.LikeImpl;
import ru.yandex.practicum.filmorate.storage.impl.MpaImpl;


import java.util.Collection;
import java.util.List;

@Component
@Qualifier("DbFilmStorage")
public class DbFilmStorage implements FilmStorage{

    private JdbcTemplate jdbcTemplate;
    private FilmImpl filmImpl;
    private LikeImpl likeImpl;
    private MpaImpl mpaImpl;
    private GenreImpl genreImpl;


    @Autowired
    public DbFilmStorage(JdbcTemplate jdbcTemplate, FilmImpl filmImpl, LikeImpl likeImpl, MpaImpl mpaImpl, GenreImpl genreImpl) {
        this.jdbcTemplate = jdbcTemplate;
        this.filmImpl=filmImpl;
        this.likeImpl=likeImpl;
        this.mpaImpl=mpaImpl;
        this.genreImpl=genreImpl;
    }


    @Override
    public Film get(int filmId) {
        Film film = filmImpl.getFilmById(filmId);
        film.setGenres(genreImpl.getAllGenresByIdWithFilm(filmId));
        return  film;
    }

    @Override
    public Film save(Film film) {
        Film films = filmImpl.saveFilm(film);
        Mpa mpa = mpaImpl.getMpaById(films.getMpa().getId());
        film.setMpa(mpa);
        genreImpl.saveGenres(film);
        film.setGenres(genreImpl.getAllGenresByIdWithFilm(films.getId()));
        return film;
    }

    @Override
    public void deleteFilm(int id) {
        genreImpl.deleteGenresInFilm(id);
        likeImpl.deleteAllLikesInFilm(id);
        filmImpl.deleteFilmById(id);
    }


    @Override
    public Film update(Film film) {
        if (checkingFilm(film.getId())) {
            filmImpl.updateFilm(film);
            Mpa mpa = mpaImpl.getMpaById(film.getMpa().getId());
            film.setMpa(mpa);
            genreImpl.deleteGenresInFilm(film.getId());
            genreImpl.saveGenres(film);
        } else {
            save(film);
        }
        return film;
    }

    @Override
    public void addLike(int filmId, int userId) {
        likeImpl.saveLikes(filmId, userId);
    }

    @Override
    public void deleteLike(int filmId, int userId) {
        likeImpl.deleteLikes(filmId, userId);
    }

    @Override
    public Collection<Film> getAllFilm() {
        return filmImpl.getAllFilms();
    }

    @Override
    public Collection<Film> getFilmPopularWithCount(int count) {
        return null;
    }

    @Override
    public Collection<Film> getFilmPopular(int count) {
        return filmImpl.getFilmPopular(count);
    }

    @Override
    public Genre getGenreById(int id) {
        return genreImpl.getGenreById(id);
    }

    @Override
    public List<Genre> getAllGenres(){
        return genreImpl.getAllGenres();

    }

    @Override
    public boolean checkingFilm(int id) {
        String sql = "SELECT * " +
                "FROM FILMS " +
                "WHERE film_id = ?";
        return jdbcTemplate.queryForRowSet(sql, id).next();
    }

    @Override
    public Mpa getMpaById(int id) {
        return mpaImpl.getMpaById(id);
    }

    @Override
    public List<Mpa> getAllMpa() {
        return mpaImpl.getAllMpa();
    }
}
