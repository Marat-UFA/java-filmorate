package ru.yandex.practicum.filmorate.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.Exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Friend;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.dao.FriendDao;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

@Component
@Qualifier("DbUserStorage")
public class DbUserStorage implements UserStorage{


    private final JdbcTemplate jdbcTemplate;
    private FriendDao friendImpl;

    @Autowired
    public DbUserStorage(JdbcTemplate jdbcTemplate, FriendDao friendImpl) {
        this.jdbcTemplate = jdbcTemplate;
        this.friendImpl=friendImpl;
    }

    @Override
    public User get(int UserId) {

        String sqlQuery = "select user_id, user_name, login, email, birthday " +
                "from USERS where user_id = ?";
        final List<User> users = jdbcTemplate.query(sqlQuery, DbUserStorage::makeUser, UserId);
        if (users.size()!=0) {
            return users.get(0);
        } else {
            throw new NotFoundException("Film with id: " + UserId + " not found");
        }

    }

    static User makeUser(ResultSet rs, int rowNum) throws SQLException {
        return new User(rs.getInt("USER_ID"),
                rs.getString("USER_NAME"),
                rs.getString("LOGIN"),
                rs.getString("EMAIL"),
                rs.getDate("BIRTHDAY").toLocalDate()
        );
    }

    @Override
    public User save(User user) {
        String sqlQuery = "insert into USERS (EMAIL, LOGIN, USER_NAME, BIRTHDAY) values (?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sqlQuery, new String[]{"USER_ID"});
            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getLogin());
            stmt.setString(3, user.getName());
            final LocalDate birthday = user.getBirthday();
            if (birthday == null) {
                stmt.setNull(4, Types.DATE);
            } else {
                stmt.setDate(4, Date.valueOf(birthday));
            }
            return stmt;
        }, keyHolder);
        user.setId(keyHolder.getKey().intValue());
        return user;
    }

    @Override
    public User update(User user) {
        String sqlQuery = "update USERS set " +
                "user_name = ?, login = ?, email = ?, birthday = ? " +
                "where user_id = ?";
        jdbcTemplate.update(sqlQuery
                , user.getName()
                , user.getLogin()
                , user.getEmail()
                , user.getBirthday()
                , user.getId());
        return user;
    }

    @Override
    public void addFriend(int userId, int friendId) {
        friendImpl.addUserWithFriend(userId, friendId);
   }

    @Override
    public void deleteFriend(int userId, int friendId) {
        friendImpl.deleteUserWithFriend(userId, friendId);
    }

    @Override
    public List<User> getAllUser() {
        String sqlQuery = "select user_id, user_name, login, email, birthday " +
                "from USERS";
        List<User> users = jdbcTemplate.query(sqlQuery, DbUserStorage::makeUser);
        return  users;
    }

    @Override
    public Collection<User> getAllFriends(int userId) {

       final List<Friend> listFriendsId = friendImpl.getAllFriends(userId);

        List friendsListID = new ArrayList();

        for (int i=0; i<listFriendsId.size(); i++){
            if (listFriendsId.get(i).getUserId() == userId) {
                friendsListID.add(listFriendsId.get(i).getFriendId());
            }
            if (listFriendsId.get(i).getFriendId() == userId) {
                friendsListID.add(listFriendsId.get(i).getUserId());
            }
        }

        Set<User> userFriends = new HashSet<>();
        for (int i=0; i<friendsListID.size(); i++){
            String sqlQuery = "select user_id, user_name, login, email, birthday " +
                    "from USERS where user_id = ?";
            final List<User> users = jdbcTemplate.query(sqlQuery, DbUserStorage::makeUser, friendsListID.get(i));
            userFriends.add(users.get(0));
        }
        return userFriends;
    }

    @Override
    public Collection<User> getMutualFriends(int userId, int friendId) {

        final List<Friend> listUserIdWithFriendId = friendImpl.getAllFriendsAndUser(userId, friendId);
        User user = new User();
        Friend friend = new Friend();
        for (int i = 0; i < listUserIdWithFriendId.size(); i++){

            if (listUserIdWithFriendId.get(i).getUserId() == userId) {
                user.getFriendID().add(listUserIdWithFriendId.get(i).getFriendId());
            }
            if (listUserIdWithFriendId.get(i).getFriendId() == userId) {
                user.getFriendID().add(listUserIdWithFriendId.get(i).getUserId());
            }
            if (listUserIdWithFriendId.get(i).getUserId() == friendId) {
                friend.getFriend_ID().add(listUserIdWithFriendId.get(i).getFriendId());
            }
            if (listUserIdWithFriendId.get(i).getFriendId() == friendId) {
                friend.getFriend_ID().add(listUserIdWithFriendId.get(i).getUserId());
            }
        }

        List friendsListID = new ArrayList();
        for (Integer entry: user.getFriendID()) {
            for (Integer entry1: friend.getFriend_ID()) {
                if (entry == entry1){
                    friendsListID.add(entry);
                }
            }
        }

        Set<User> userFriends = new HashSet<>();
        for (int i = 0; i < friendsListID.size(); i++){
            String sqlQuery = "select user_id, user_name, login, email, birthday " +
                    "from USERS where user_id = ?";
            final List<User> users = jdbcTemplate.query(sqlQuery, DbUserStorage::makeUser, friendsListID.get(i));
            userFriends.add(users.get(0));

        }
        System.out.println(userFriends);
        return userFriends;
    }

    @Override
    public boolean checkingUser(int id) {
        String sql = "SELECT * " +
                "FROM USERS " +
                "WHERE user_id = ?";
        return jdbcTemplate.queryForRowSet(sql, id).next();
    }

    @Override
    public void deleteUser(int userId) {
        deleteAllFriendsByUser(userId);
        String sqlQuery = "DELETE " +
                "FROM USERS " +
                "WHERE user_id = ?";
        jdbcTemplate.update(sqlQuery, userId);
    }

    @Override
    public void deleteAllFriendsByUser(int userId) {
        friendImpl.deleteAllFriendsByUser(userId);
    }

}
