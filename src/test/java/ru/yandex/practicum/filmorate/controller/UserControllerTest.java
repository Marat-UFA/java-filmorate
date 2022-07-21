package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import ru.yandex.practicum.filmorate.Exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;



class UserControllerTest {

    @Test
    public void createNewUserTest() {
        User user = new User (2,"asd@mail.ru","131321","qwerty", LocalDate.parse("06.06.2022",
                DateTimeFormatter.ofPattern("dd.MM.yyyy")), new TreeSet<>());
        InMemoryUserStorage userController = new InMemoryUserStorage();
        userController.save(user);
        assertEquals(userController.getAllUser().size(),1,"добавлен один пользователь");
    }

    @Test
    public void createNewUserBadEmailTest() {
        User user = new User (2,"asdmail.ru","131321","qwerty", LocalDate.parse("06.06.2022",
                DateTimeFormatter.ofPattern("dd.MM.yyyy")), new TreeSet<>());
        UserController userController = new UserController();
        try {
            userController.validate (user);
        } catch (ValidationException e) {
            ValidationException validationException = new ValidationException(e.getMessage());
            assertEquals(validationException.toString(),"электронная почта не может быть пустой и должна содержать символ @",
                    "Не верно указана почта");
        }
    }


    @Test
    public void createNewUserBadBirthdayTest() {
        User user = new User (2,"asd@mail.ru","131321","qwerty", LocalDate.parse("06.06.2029",
                DateTimeFormatter.ofPattern("dd.MM.yyyy")), new TreeSet<>());
        UserController userController = new UserController();
        try {
            userController.validate (user);
        } catch (ValidationException e) {
            ValidationException validationException = new ValidationException(e.getMessage());
            assertEquals(validationException.toString(),"дата рождения не может быть в будущем",
                    "Не верно указан день рождения");
        }
    }

    @Test
    public void createNewUserBadIDTest() {
        User user = new User (-10,"asd@mail.ru","131321","qwerty", LocalDate.parse("06.06.2022",
                DateTimeFormatter.ofPattern("dd.MM.yyyy")), new TreeSet<>());
        UserController userController = new UserController();
        try {
            userController.validate (user);
        } catch (ValidationException e) {
            ValidationException validationException = new ValidationException(e.getMessage());
            assertEquals(validationException.toString(),"id < 0",
                    "id не может бвть отрицательным");
        }
    }

    @Test
    public void createNewUserNullNameTest() throws ValidationException {
        User user = new User (1,"asd@mail.ru","131321","", LocalDate.parse("06.06.2022",
                DateTimeFormatter.ofPattern("dd.MM.yyyy")), new TreeSet<>());

        UserController userController = new UserController();
        userController.validate (user);
        assertEquals(user.getName(),user.getLogin(), "имя заменено на 131321");
    }
}
