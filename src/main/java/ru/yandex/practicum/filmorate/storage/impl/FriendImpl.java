package ru.yandex.practicum.filmorate.storage.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Friend;
import ru.yandex.practicum.filmorate.storage.dao.FriendDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class FriendImpl implements FriendDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FriendImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addUserWithFriend(int userId, int friendId) {
        String sqlQuery = "insert into FRIENDS (USER_ID, FRIEND_ID, STATUS) values (?, ?, ?)";
        Boolean status = false;
        jdbcTemplate.update(sqlQuery, userId, friendId, status);
    }

    @Override
    public void deleteUserWithFriend(int userId, int friendId) {
        String sqlQuery = "delete from FRIENDS" +
                " where user_id = "+ userId + "and friend_id = " + friendId;
        jdbcTemplate.update(sqlQuery);
    }

    @Override
    public List<Friend> getAllFriendsAndUser(int userId, int friendId) {
        String sqlQuery = "select user_id, friend_id " +
                "from FRIENDS " +
                "where user_id = " + userId + "or (friend_id = " + userId + " and  status = true) or user_id = "
                + friendId + " or (friend_id = " + friendId + " and  status = true)";
        List<Friend> listUserAndFriendId = jdbcTemplate.query(sqlQuery, FriendImpl::makeFriend);
        return listUserAndFriendId;
    }

    @Override
    public List<Friend> getAllFriends(int userId) {
        String sqlQuery = "select user_id, friend_id " +
                "from FRIENDS " +
                "where user_id = " + userId + "or (friend_id = " + userId + " and  status = true)";
        return jdbcTemplate.query(sqlQuery, FriendImpl::makeFriend);
    }

    @Override
    public void deleteAllFriendsByUser(int userId) {
        String sqlQuery = "DELETE " +
                "FROM FRIENDS " +
                "WHERE user_id = ?";
        jdbcTemplate.update(sqlQuery, userId);
    }

    static Friend makeFriend(ResultSet rs, int rowNum) throws SQLException {
        return new Friend(rs.getInt("USER_ID"),
                rs.getInt("FRIEND_ID")
        );
    }
}
