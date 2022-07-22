package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Component
public class InMemoryUserStorage implements UserStorage {

    int generator = 0;

    HashMap<Integer, User> userMap = new HashMap<>();

    @Override
    public User get (int UserId){
        return userMap.get(UserId);
    }

    @Override
    public User save (User user){
        user.setId(++generator);
        userMap.put(user.getId(), user);
        return user;
    }

    @Override
    public User update (User user){
        userMap.remove(user.getId(), user);
        userMap.put(user.getId(), user);
        return user;
    }

    @Override
    public Collection<User> getAllUser (){
        return userMap.values();
    }

    @Override
    public void addFriend(User user, User friend) {
        user.getFriendID().add(friend.getId());
        friend.getFriendID().add(user.getId());
    }

    @Override
    public void deleteFriend(User user, User friend) {
        user.getFriendID().remove(friend.getId());
        friend.getFriendID().remove(user.getId());
    }

    @Override
    public Collection<User> getAllFriends(User user) {
        Set<User> userFriends = new HashSet<>();
        for (Integer entry: user.getFriendID()) {
            userFriends.add(userMap.get(entry));
        }
        return userFriends;
    }

    @Override
    public Collection<User> getMutualFriends(User user, User friend) {
        Set<User> userFriends = new HashSet<>();
        for (Integer entry: user.getFriendID()) {
            for (Integer entry1: friend.getFriendID()) {
                if (entry == entry1){
                    userFriends.add(userMap.get(entry));
                }
            }
        }
        return userFriends;
    }
}
