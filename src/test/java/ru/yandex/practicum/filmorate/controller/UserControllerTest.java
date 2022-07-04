package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import ru.yandex.practicum.filmorate.Exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import static org.junit.jupiter.api.Assertions.*;


@WebMvcTest(controllers = UserController.class)
class UserControllerTest {

    @Test
    public void createNewUserTest() throws ValidationException {
        User user = new User (2,"asd@mail.ru","131321","qwerty", LocalDate.parse("06.06.2022", DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        UserController userController = new UserController();
        userController.saveUser(user);

        assertEquals(userController.findAll().size(),1,"добавлен один пользователь");
    }

    @Test
    public void createNewUserBadEmailTest() {
        User user = new User (2,"asdmail.ru","131321","qwerty", LocalDate.parse("06.06.2022", DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        UserController userController = new UserController();
        try {
            userController.validate (user);
        } catch (ValidationException e) {
            System.out.println("Проверка на не верную почту. Сообщение: ");
            ValidationException validationException = new ValidationException(e.getMessage());
            assertEquals(validationException.toString(),"электронная почта не может быть пустой и должна содержать символ @",
                    "Не верно указана почта");
        }
    }

    @Test
    public void createNewUserBadLoginTest() {
        User user = new User (2,"asd@mail.ru","131 321","qwerty", LocalDate.parse("06.06.2022", DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        UserController userController = new UserController();
        try {
            userController.validate (user);
        } catch (ValidationException e) {
            System.out.println("Проверка на не верный логин. Сообщение: ");
            ValidationException validationException = new ValidationException(e.getMessage());
            assertEquals(validationException.toString(),"логин не может быть пустым и содержать пробелы",
                    "Не верно указан логин");
        }
    }

    @Test
    public void createNewUserBadBirthdayTest() {
        User user = new User (2,"asd@mail.ru","131321","qwerty", LocalDate.parse("06.06.2029", DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        UserController userController = new UserController();
        try {
            userController.validate (user);
        } catch (ValidationException e) {
            System.out.println("Проверка на не верный день рождения. Сообщение: ");
            ValidationException validationException = new ValidationException(e.getMessage());
            assertEquals(validationException.toString(),"дата рождения не может быть в будущем",
                    "Не верно указан день рождения");
        }
    }

    @Test
    public void createNewUserBadIDTest() {
        User user = new User (-10,"asd@mail.ru","131321","qwerty", LocalDate.parse("06.06.2022", DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        UserController userController = new UserController();
        try {
            userController.validate (user);
        } catch (ValidationException e) {
            System.out.println("Проверка на не верный id. Сообщение: ");
            ValidationException validationException = new ValidationException(e.getMessage());
            assertEquals(validationException.toString(),"id < 0",
                    "id не может бвть отрицательным");
        }
    }

    @Test
    public void createNewUserNullNameTest() throws ValidationException {
        User user = new User (1,"asd@mail.ru","131321","", LocalDate.parse("06.06.2022", DateTimeFormatter.ofPattern("dd.MM.yyyy")));

        UserController userController = new UserController();
        userController.validate (user);
        assertEquals(user.getName(),user.getLogin(), "имя заменено на 131321");
    }
}
