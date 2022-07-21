package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.Exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.util.Collection;

@Service
public class UserService {
    @Autowired
    InMemoryUserStorage userStorage;

    public User get(int userId) {
        final User user = userStorage.get(userId);
        if (user == null) {
            throw new NotFoundException("User with id=" + userId + " not found");
        }
        if (userId <= 0) {
            throw new NotFoundException("userId < 0");
        }
        return user;
    }

    public User save(User user) {
        return userStorage.save(user);
    }

    public User update(User user) {
        if (user.getId() <= 0) {
            throw new NotFoundException("userId < 0");
        }
        return userStorage.update(user);
    }

    public Collection<User> getAllUser() {
        return userStorage.getAllUser();
    }

    public void addFriend(int userId, int friendId) {
        final User user = userStorage.get(userId);
        final User friend = userStorage.get(friendId);

        if (user == null) {
            throw new NotFoundException("User with id=" + userId + " not found");
        }
        if (friend == null) {
            throw new NotFoundException("User with id=" + friendId + " not found");
        }
        userStorage.addFriend(user, friend);
    }

    public void deleteFriend(int userId, int friendId) {
        final User user = userStorage.get(userId);
        final User friend = userStorage.get(friendId);
        if (user == null) {
            throw new NotFoundException("User with id=" + userId + " not found");
        }
        if (friend == null) {
            throw new NotFoundException("User with id=" + friendId + " not found");
        }
        userStorage.deleteFriend(user, friend);
    }

    public Collection<User>  getAllFriends(int userId) {
        final User user = userStorage.get(userId);
        if (user == null) {
            throw new NotFoundException("User with id=" + userId + " not found");
        }
        return userStorage.getAllFriends(user);
    }

    public Collection<User> getMutualFriends(int userId, int otherId) {
        final User user = userStorage.get(userId);
        final User friend = userStorage.get(otherId);

        if (user == null) {
            throw new NotFoundException("User with id=" + userId + " not found");
        }
        if (friend == null) {
            throw new NotFoundException("User with id=" + otherId + " not found");
        }
        return userStorage.getMutualFriends(user, friend);
    }
}
