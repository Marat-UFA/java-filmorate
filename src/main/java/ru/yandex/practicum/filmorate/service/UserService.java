package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.Exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;

@Service
public class UserService {

    private UserStorage userStorage;
    @Autowired
    public UserService(@Qualifier("DbUserStorage") UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public User get(int userId) {


        if (userId <= 0) {
            throw new NotFoundException("userId < 0");
        }
        final User user = userStorage.get(userId);
        if (user == null) {
            throw new NotFoundException("User with id=" + userId + " not found");
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

        if (userId <= 0) {
            throw new NotFoundException("userId < 0");
        }
        if (friendId <= 0) {
            throw new NotFoundException("friendId < 0");
        }
        userStorage.addFriend(userId, friendId);
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
        userStorage.deleteFriend(userId, friendId);
    }

    public Collection<User>  getAllFriends(int userId) {

        return userStorage.getAllFriends(userId);
    }

    public Collection<User> getMutualFriends(int userId, int friendId) {

        if (userId <= 0) {
            throw new NotFoundException("userId < 0");
        }
        if (friendId <= 0) {
            throw new NotFoundException("friendId < 0");
        }

        Collection<User> user = userStorage.getMutualFriends(userId, friendId);
        if (user == null){
            throw new NotFoundException("дружбы между User с id=" + userId + " и User с id=" + friendId + " нет");
        }

        return user;
    }
}
