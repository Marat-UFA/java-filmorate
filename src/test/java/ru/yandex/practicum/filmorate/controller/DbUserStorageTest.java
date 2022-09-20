package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.Exception.NotFoundException;
import ru.yandex.practicum.filmorate.Exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.DbUserStorage;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;



@SpringBootTest
@AutoConfigureTestDatabase
class DbUserStorageTest {

    private DbUserStorage dbUserStorage;

    @Autowired
    public DbUserStorageTest(DbUserStorage dbUserStorage) {
        this.dbUserStorage = dbUserStorage;
    }

    private User createUser_1() {
        User user = new User("User_1",
                "mail_1@email.ru",
                "login_1",
                LocalDate.of(2000, 10, 10));
        return user;
    }

    private User createUser_2() {
        User user = new User("User_2",
                "mail_2@email.ru",
                "login_2",
                LocalDate.of(2002, 12, 12));
        return user;
    }

    private User createUser_3() {
        User user = new User("User_3",
                "mail_3@email.ru",
                "login_3",
                LocalDate.of(2003, 3, 13));
        return user;
    }

    @Test
    void userFindByIdTest() {
        User user = dbUserStorage.save(createUser_1());
        Optional<User> userOptional = Optional.ofNullable(dbUserStorage.get(user.getId()));
        assertNotNull(userOptional);
        assertThat(userOptional)
                .isPresent()
                .hasValueSatisfying(u ->
                        assertThat(u).hasFieldOrPropertyWithValue("name", "User_1"));
       // assertThrows(NotFoundException.class, () -> dbUserStorage.get(-1));
        dbUserStorage.deleteUser(user.getId());
    }

    @Test
    void updateUserTest() {
        User user1 = createUser_1();
        dbUserStorage.save(user1);
        User user2 = createUser_2();
        user2.setId(user1.getId());
        dbUserStorage.update(user2);
        Optional<User> userOptional = Optional.ofNullable(dbUserStorage.get(user2.getId()));
        assertNotNull(userOptional);
        assertThat(userOptional)
                .isPresent()
                .hasValueSatisfying(u ->
                        assertThat(u).hasFieldOrPropertyWithValue("name", "User_2")
                );
        dbUserStorage.deleteUser(user2.getId());
    }

    @Test
    void deleteUserTest() {
        User user = createUser_1();
        dbUserStorage.save(user);
        Integer userDelete = user.getId();
        dbUserStorage.deleteUser(userDelete);
        assertThrows(NotFoundException.class, () -> dbUserStorage.get(userDelete));
    }
}
