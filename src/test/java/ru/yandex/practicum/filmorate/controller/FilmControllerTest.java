package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.yandex.practicum.filmorate.Exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;

class FilmControllerTest {


    @Test
    public void createNewFilmTest() {
        Film film = new Film (1, "Форсаж","гонки", LocalDate.parse("28.12.1895",
                DateTimeFormatter.ofPattern("dd.MM.yyyy")), 90, new TreeSet<>());
        InMemoryFilmStorage filmController = new InMemoryFilmStorage();
        filmController.save(film);
        assertEquals(filmController.getAllFilm().size(),1,"добавлен один фильм");
    }


    @Test
    public void createNewFilmNullIdTest() {
        Film film = new Film (null, "Форсаж","гонки", LocalDate.parse("06.06.2022",
                DateTimeFormatter.ofPattern("dd.MM.yyyy")), 90, null);
        FilmController filmController = new FilmController();
        try {
            filmController.validate (film);
        } catch (ValidationException e) {
            ValidationException validationException = new ValidationException(e.getMessage());
            assertEquals(validationException.toString(),"id пустой",
                    "Не указан id");
        }
    }

    @Test
    public void createNewFilmBadIdTest() {
        Film film = new Film (-1, "Форсаж","гонки", LocalDate.parse("06.06.2022",
                DateTimeFormatter.ofPattern("dd.MM.yyyy")), 90, new TreeSet<>());
        InMemoryFilmStorage filmController = new InMemoryFilmStorage();;
        filmController.save(film);
        assertEquals(filmController.get(1).getId(),1,"id = 1");
    }


    @Test
    public void createNewFilmNullDataTest() {
        Film film = new Film (1, "Форсаж","гонки", null, 90, null);
        FilmController filmController = new FilmController();
        try {
            filmController.validate (film);
        } catch (ValidationException e) {
            ValidationException validationException = new ValidationException(e.getMessage());
            assertEquals(validationException.toString(),"Дата не указана",
                    "Дата не указана");
        }
    }

    @Test
    public void createNewFilmBadDataTest() {
        Film film = new Film (5, "Форсаж","гонки", LocalDate.parse("27.12.1895",
                DateTimeFormatter.ofPattern("dd.MM.yyyy")), 90, null);
        FilmController filmController = new FilmController();
        try {
            filmController.validate (film);
        } catch (ValidationException e) {
            ValidationException validationException = new ValidationException(e.getMessage());
            assertEquals(validationException.toString(),"Дата релиза — не может быть раньше 1895-12-28",
                    "Дата релиза — не может быть раньше 1895-12-28");
        }
    }

}
