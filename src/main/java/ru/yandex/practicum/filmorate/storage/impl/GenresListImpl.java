package ru.yandex.practicum.filmorate.storage.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.dao.GenresListDao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class GenresListImpl implements GenresListDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GenresListImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void saveGenres(Film film) {
        String sqlQuery = "INSERT INTO GENRES_LIST (film_id, genre_id) " +
                "VALUES (" + film.getId() + ", ?)";
        List<Genre> genres = film.getGenres();
        if (genres != null) {
            Set<Genre> setGenres = new HashSet<>(genres);
            genres = new ArrayList<>(setGenres);
            film.setGenres(genres);
            for (Genre genre : genres) {
                jdbcTemplate.update(sqlQuery, genre.getId());
            }
        }

    }

    @Override
    public Set<Integer> getAllGenresByIdWithFilm(int filmId) {
        String sqlQuery = "select genre_id " +
                "from GENRES_LIST " +
                "where film_id = ?";
        Set<Integer> set = new HashSet<>(jdbcTemplate.query(sqlQuery, ((rs, rowNum) -> rs.getInt("genre_id")), filmId));
        return set;
    }

    @Override
    public void deleteGenresInFilm(int id) {
        String sqlQuery = "DELETE " +
                "FROM GENRES_LIST " +
                "WHERE film_id = ?";
        jdbcTemplate.update(sqlQuery, id);
    }
}
