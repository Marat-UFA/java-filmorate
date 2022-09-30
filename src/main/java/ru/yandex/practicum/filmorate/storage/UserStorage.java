package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.List;

public interface UserStorage {

    User get (int UserId);
    User save (User user);
    User update (User user);
    void addFriend(int userId, int friendId);
    void deleteFriend(int userId, int friendId);
    List<User> getAllUser ();
    Collection<User> getAllFriends(int userId);
    Collection<User> getMutualFriends(int userId, int friendId);
    boolean checkingUser(int id);
    void deleteUser (int UserId);
    void deleteAllFriendsByUser(int UserId);

}
