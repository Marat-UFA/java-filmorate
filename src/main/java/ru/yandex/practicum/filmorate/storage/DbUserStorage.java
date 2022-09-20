package ru.yandex.practicum.filmorate.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.Exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Friend;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

@Component
@Qualifier("DbUserStorage")
public class DbUserStorage implements UserStorage{


    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DbUserStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
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
        String sqlQuery = "insert into FRIENDS (USER_ID, FRIEND_ID, STATUS) values (?, ?, ?)";
        Boolean status = false;
        jdbcTemplate.update(sqlQuery, userId, friendId, status);
    }

    @Override
    public void deleteFriend(int userId, int friendId) {
            String sqlQuery = "delete from FRIENDS" +
                    " where user_id = "+ userId + "and friend_id = " + friendId;
            jdbcTemplate.update(sqlQuery);
    }

    @Override
    public Collection<User> getAllUser() {
        String sqlQuery = "select user_id, user_name, login, email, birthday " +
                "from USERS";
        List<User> users = jdbcTemplate.query(sqlQuery, DbUserStorage::makeUser);
        return  users;
    }

    @Override
    public Collection<User> getAllFriends(int userId) {
        String sqlQuery = "select user_id, friend_id " +
                "from FRIENDS " +
                "where user_id = " + userId + "or (friend_id = " + userId + " and  status = true)";
        final List<Friend> listUserId = jdbcTemplate.query(sqlQuery, DbUserStorage::makeFriend);

        List friendsListID = new ArrayList();

        for (int i=0; i<listUserId.size(); i++){
            if (listUserId.get(i).getUserId() == userId) {
                friendsListID.add(listUserId.get(i).getFriendId());
            }
            if (listUserId.get(i).getFriendId() == userId) {
                friendsListID.add(listUserId.get(i).getUserId());
            }

        }

        Set<User> userFriends = new HashSet<>();
        for (int i=0; i<friendsListID.size(); i++){
            sqlQuery = "select user_id, user_name, login, email, birthday " +
                    "from USERS where user_id = ?";
            final List<User> users = jdbcTemplate.query(sqlQuery, DbUserStorage::makeUser, friendsListID.get(i));
            userFriends.add(users.get(0));
        }
        return userFriends;
    }

    @Override
    public Collection<User> getMutualFriends(int userId, int friendId) {

        String sqlQuery = "select user_id, friend_id " +
                "from FRIENDS " +
                "where user_id = " + userId + "or (friend_id = " + userId + " and  status = true) or user_id = "
                + friendId + " or (friend_id = " + friendId + " and  status = true)";

        final List<Friend> listUserIdWithFriendId = jdbcTemplate.query(sqlQuery, DbUserStorage::makeFriend);
        User user = new User();
        Friend friend = new Friend();
        for (int i=0; i<listUserIdWithFriendId.size(); i++){

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
        for (int i=0; i<friendsListID.size(); i++){
            sqlQuery = "select user_id, user_name, login, email, birthday " +
                    "from USERS where user_id = ?";
            final List<User> users = jdbcTemplate.query(sqlQuery, DbUserStorage::makeUser, friendsListID.get(i));
            userFriends.add(users.get(0));
        }
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
    public void deleteUser(int UserId) {
        deleteAllFriendsByUser(UserId);
        String sqlQuery = "DELETE " +
                "FROM USERS " +
                "WHERE user_id = ?";
        jdbcTemplate.update(sqlQuery, UserId);
    }

    @Override
    public void deleteAllFriendsByUser(int UserId) {
        String sqlQuery = "DELETE " +
                "FROM FRIENDS " +
                "WHERE user_id = ?";
        jdbcTemplate.update(sqlQuery, UserId);
    }

    static Friend makeFriend(ResultSet rs, int rowNum) throws SQLException {
        return new Friend(rs.getInt("USER_ID"),
                rs.getInt("FRIEND_ID")
        );
    }
}
