package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.Exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;


import java.time.LocalDate;
import java.util.Collection;



@RestController
@Slf4j
@RequestMapping("users")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping()
    User saveUser(@RequestBody User user) throws ValidationException {
        validate(user);
        User saved = userService.save(user);
        return saved;
    }

    @PutMapping()
    User updateUser(@RequestBody User user) throws ValidationException {
        validate(user);
        User update = userService.update(user);
        return update;
    }

    @GetMapping("/{userId}")
    User getUser (@PathVariable int userId){
        log.info("Get user by id={}",userId);
        return userService.get(userId);
    }

    @GetMapping()
    Collection<User> getAllUser (){
        return userService.getAllUser();
    }

    @GetMapping("/{userId}/friends")
    Collection<User>  getAllFriends (@PathVariable int userId){
        log.info("Get all friends user by id={}",userId);
        return userService.getAllFriends(userId);
    }

    @GetMapping("/{userId}/friends/common/{otherId}")
    Collection<User>  getMutualFriends (@PathVariable int userId, @PathVariable int otherId){
        log.info("Get all mutual friends user by id= " + userId + " with user by id= " + otherId);
        return userService.getMutualFriends(userId, otherId);
    }



    @PutMapping("/{userId}/friends/{friendId}")
    public void addFriend (@PathVariable int userId, @PathVariable int friendId){
        userService.addFriend(userId, friendId);
    }

    @DeleteMapping("/{userId}/friends/{friendId}")
    public void deleteFriend (@PathVariable int userId, @PathVariable int friendId){
        userService.deleteFriend(userId, friendId);
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
    }
}
