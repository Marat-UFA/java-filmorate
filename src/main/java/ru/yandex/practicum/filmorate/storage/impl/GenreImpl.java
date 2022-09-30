package ru.yandex.practicum.filmorate.storage.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import ru.yandex.practicum.filmorate.storage.dao.GenreDao;
import ru.yandex.practicum.filmorate.storage.dao.GenresListDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class GenreImpl implements GenreDao {


    private final JdbcTemplate jdbcTemplate;
    private GenresListDao genresListImpl;

    @Autowired
    public GenreImpl(JdbcTemplate jdbcTemplate, GenresListDao genresListImpl) {
        this.jdbcTemplate = jdbcTemplate;
        this.genresListImpl = genresListImpl;
    }

    @Override
    public void saveGenres(Film film) {
        genresListImpl.saveGenres(film);
    }

    @Override
    public List<Genre> getAllGenresByIdWithFilm(int filmId) {
        Set<Integer> set = genresListImpl.getAllGenresByIdWithFilm(filmId);
        List<Genre> genre = new ArrayList<>();
        for (Integer genreId: set) {
            genre.add(getGenreById( genreId));
        }
        return genre;
    }

    @Override
    public void deleteGenresInFilm(int id) {
        genresListImpl.deleteGenresInFilm(id);
    }


    static Genre makeGenre(ResultSet rs, int rowNum) throws SQLException {
        return new Genre(rs.getInt("GENRE_ID"),
                rs.getString("GENRE_NAME")
        );
    }

    @Override
    public Genre getGenreById(int genreid) {
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
