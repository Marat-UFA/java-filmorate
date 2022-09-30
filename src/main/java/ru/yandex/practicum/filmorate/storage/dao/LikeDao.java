package ru.yandex.practicum.filmorate.storage.dao;

public interface LikeDao {
    void saveLikes(int filmId, int userId);
    void deleteLikes(int filmId, int userId);
    void deleteAllLikesInFilm(int id);
}
