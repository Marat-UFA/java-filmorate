package ru.yandex.practicum.filmorate.storage.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.storage.dao.LikeDao;


@Component
public class LikeImpl implements LikeDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public LikeImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void saveLikes(int filmId, int userId) {
        String sqlQuery = "INSERT INTO LIKES_LIST (film_id, user_id) " +
                "VALUES (" + filmId + ", " + userId + ")";
                jdbcTemplate.update(sqlQuery);

        sqlQuery = "UPDATE films " +
                "SET rating = rating + " + 1 +
                "WHERE film_id = ?";
        jdbcTemplate.update(sqlQuery, filmId);
}

    @Override
    public void deleteLikes(int filmId, int userId) {
        String sqlQuery = "DELETE " +
                "FROM LIKES_LIST " +
                "WHERE film_id = " + filmId + "and user_id = " + userId;
        jdbcTemplate.update(sqlQuery);
        sqlQuery = "UPDATE films " +
                "SET rating = rating - " + 1 +
                "WHERE film_id = ?";
        jdbcTemplate.update(sqlQuery, filmId);
    }

    @Override
    public void deleteAllLikesInFilm(int id) {
        String sqlQuery = "DELETE " +
                "FROM LIKES_LIST " +
                "WHERE film_id = ?";
        jdbcTemplate.update(sqlQuery, id);
    }
}
