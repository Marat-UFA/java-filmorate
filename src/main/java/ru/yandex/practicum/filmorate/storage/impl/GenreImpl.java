package ru.yandex.practicum.filmorate.storage.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import ru.yandex.practicum.filmorate.storage.dao.GenreDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class GenreImpl implements GenreDao {


    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GenreImpl(JdbcTemplate jdbcTemplate) {
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
    public List<Genre> getAllGenresByIdWithFilm(Integer genreid) {
        String sqlQuery = "select genre_id " +
                "from GENRES_LIST " +
                "where film_id = ?";
        Set<Integer> set = new HashSet<>(jdbcTemplate.query(sqlQuery, ((rs, rowNum) -> rs.getInt("genre_id")), genreid));
        List<Genre> genre = new ArrayList<>();
        for (Integer genreId: set) {
            genre.add(getGenreById( genreId));
        }
        return genre;
    }

    @Override
    public void deleteGenresInFilm(Integer id) {
        String sqlQuery = "DELETE " +
                "FROM GENRES_LIST " +
                "WHERE film_id = ?";
        jdbcTemplate.update(sqlQuery, id);
    }


    static Genre makeGenre(ResultSet rs, int rowNum) throws SQLException {
        return new Genre(rs.getInt("GENRE_ID"),
                rs.getString("GENRE_NAME")
        );
    }

    @Override
    public Genre getGenreById(Integer genreid) {
        String sqlQuery = "select * " +
                "from GENRE "+
                "where genre_id = ?";
        Genre genre = new Genre();
        List <Genre> genres = jdbcTemplate.query(sqlQuery, GenreImpl::makeGenre, genreid);
        return genres.get(0);

    }

    @Override
    public List<Genre> getAllGenres() {
        String sqlQuery = "select * " +
                "from GENRE ";
        return jdbcTemplate.query(sqlQuery, GenreImpl::makeGenre);
    }
}
