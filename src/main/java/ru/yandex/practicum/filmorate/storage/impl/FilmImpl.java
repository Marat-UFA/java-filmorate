package ru.yandex.practicum.filmorate.storage.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.Exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.dao.FilmDao;
import ru.yandex.practicum.filmorate.storage.dao.GenreDao;

import java.sql.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Component
public class FilmImpl implements FilmDao {
    private final JdbcTemplate jdbcTemplate;
    private GenreDao genreImpl;

    @Autowired
    public FilmImpl(JdbcTemplate jdbcTemplate, GenreImpl genreImpl) {
        this.jdbcTemplate = jdbcTemplate;
        this.genreImpl=genreImpl;
    }


    @Override
    public Film saveFilm(Film film) {

        String sqlQuery = "insert into FILMS (FILM_NAME, DESCRIPTION, RELEASE_DATE, DURATION, MPA_ID, RATING)" +
                "values (?, ?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sqlQuery, new String[]{"FILM_ID"});
            stmt.setString(1, film.getName());
            stmt.setString(2, film.getDescription());
            final LocalDate releaseDate = film.getReleaseDate();
            if (releaseDate == null) {
                stmt.setNull(3, Types.DATE);
            } else {
                stmt.setDate(3, Date.valueOf(releaseDate));
            }
            stmt.setInt(4, film.getDuration());
            stmt.setInt(5, film.getMpa().getId());
            stmt.setInt(6, film.getRating());
            return stmt;
        }, keyHolder);
        film.setId(keyHolder.getKey().intValue());
        return film;
    }

    @Override
    public Film getFilmById(int id) {
        String sqlQuery = "SELECT f.*, m.mpa_name " +
                "FROM FILMS as f Join MPA as m ON f.mpa_id=m.mpa_id " +
                "WHERE f.film_id = ?";
        List<Film> getFilm = jdbcTemplate.query(sqlQuery, FilmImpl::makeFilm, id);
        if (getFilm.size()!=0) {
            Film film = getFilm.get(0);
            return film;
        } else {
            throw new NotFoundException("Film with id: " + id + " not found");
        }
    }

    @Override
    public void deleteFilmById(int id) {
        String sqlQuery = "DELETE " +
                "FROM FILMS " +
                "WHERE film_id = ?";
        jdbcTemplate.update(sqlQuery, id);
    }

    @Override
    public List<Film> getAllFilms() {
        String sqlQuery = "SELECT f.*, m.mpa_name " +
                "FROM FILMS as f Join MPA as m ON f.mpa_id=m.mpa_id ";
        List<Film> filmRows = jdbcTemplate.query(sqlQuery, FilmImpl::makeFilm);
        List<Film> filmRowsNew = null;
        for (Film film: filmRows) {
            film.setGenres(genreImpl.getAllGenresByIdWithFilm(film.getId()));
        }
        return filmRows;

    }

    static Film makeFilm(ResultSet rs, int rowNum) throws SQLException {
        return new Film(rs.getInt("FILM_ID"),
                rs.getString("FILM_NAME"),
                rs.getString("DESCRIPTION"),
                rs.getDate("RELEASE_DATE").toLocalDate(),
                rs.getInt("DURATION"),
                new Mpa(rs.getInt("MPA_ID"),
                        rs.getString("MPA_NAME")),
                rs.getInt("RATING")
        );
    }

    @Override
    public Film updateFilm(Film film) {
        String sqlQuery = "UPDATE films " +
                "SET film_name = ?, description = ?, release_date = ?, duration = ?, mpa_id = ? " +
                "WHERE film_id = ?";
        jdbcTemplate.update(sqlQuery, film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getMpa().getId(),
                film.getId());
        return film;
    }

    @Override
    public List<Film> getFilmPopular(int count) {
        String sqlQuery = "SELECT f.*, m.mpa_name " +
                "FROM FILMS as f Join MPA as m ON f.mpa_id=m.mpa_id " +
                "GROUP BY film_id " +
                "ORDER BY rating DESC " +
                "LIMIT " + count;

        List<Film> getFilmPopular = jdbcTemplate.query(sqlQuery, FilmImpl::makeFilm);
        return getFilmPopular;
    }
}
