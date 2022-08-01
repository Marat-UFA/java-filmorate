package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface UserStorage {

    User get (int UserId);
    User save (User user);
    User update (User user);
    void addFriend(User user, User friend);
    void deleteFriend(User user, User friend);
    Collection<User> getAllUser ();
    Collection<User> getAllFriends(User user);
    Collection<User> getMutualFriends(User user, User friend);


}
