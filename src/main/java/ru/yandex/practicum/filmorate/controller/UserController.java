package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.Exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@RestController
@Slf4j
public class UserController {

    private final List<User> users = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;

    @PostMapping(value = "/users")
    User saveUser(@RequestBody User user) throws ValidationException {
        setId(id+1);
        user.setId(getId());
        validate(user);
        log.info("Пользователь добавлен");
        users.add(user);
        return user;
    }
    @PutMapping(value = "/users")
    User updateUser(@RequestBody User user) throws ValidationException {
        validate(user);
        for (int i=0; i < users.size(); i++){
            if (users.get(i).getId() == user.getId()){
                users.get(i).setEmail(user.getEmail());
                users.get(i).setLogin(user.getLogin());
                users.get(i).setName(user.getName());
                users.get(i).setBirthday(user.getBirthday());
                log.info("Ползователь обновлен");
                return user;
            }
        }
        users.add(user);
        return user;
    }

    @GetMapping("/users")
    public List<User> findAll() {
        log.debug("Текущее количество пользователей: {}", users.size());
        return users;
    }

    void validate (User user) throws ValidationException {

        if (user.getEmail() == null || !user.getEmail().contains("@")) {
            throw new ValidationException("электронная почта не может быть пустой и должна содержать символ @");
        }

        if (user.getName().isEmpty() || user.getName().isBlank()){
            user.setName(user.getLogin());
        }

        if (user.getBirthday().isAfter(LocalDate.now())){
            throw new ValidationException("дата рождения не может быть в будущем");
        }

        if (user.getId()<=0){
            throw new ValidationException("id < 0");
        }
    }

}
