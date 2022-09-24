package ru.yandex.practicum.filmorate.storage.dao;

import ru.yandex.practicum.filmorate.model.Friend;

import java.util.List;

public interface FriendDao {
    void addUserWithFriend(int userId, int friendId);
    void deleteUserWithFriend(int userId, int friendId);
    List<Friend> getAllFriendsAndUser(int userId, int friendId);
    List<Friend> getAllFriends(int userId);
    void deleteAllFriendsByUser(int userId);

}
