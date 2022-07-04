package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.Exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class FilmControllerTest {

    @Test
    public void createNewFilmTest() throws ValidationException {
        Film film = new Film (1, "Форсаж","гонки", LocalDate.parse("28.12.1895", DateTimeFormatter.ofPattern("dd.MM.yyyy")), 90);
        FilmController filmController = new FilmController();
        filmController.saveFilm(film);
        assertEquals(filmController.findAll().size(),1,"добавлен один фильм");
    }


    @Test
    public void createNewFilmNullIdTest() {
        Film film = new Film (null, "Форсаж","гонки", LocalDate.parse("06.06.2022", DateTimeFormatter.ofPattern("dd.MM.yyyy")), 90);
        FilmController filmController = new FilmController();
        try {
            filmController.validate (film);
        } catch (ValidationException e) {
            System.out.println("Проверка на пустой id. Сообщение: ");
            ValidationException validationException = new ValidationException(e.getMessage());
            assertEquals(validationException.toString(),"id пустой",
                    "Не указан id");
        }
    }

    @Test
    public void createNewFilmBadIdTest() throws ValidationException {
        Film film = new Film (-1, "Форсаж","гонки", LocalDate.parse("06.06.2022", DateTimeFormatter.ofPattern("dd.MM.yyyy")), 90);
        FilmController filmController = new FilmController();
        filmController.saveFilm(film);
        assertEquals(filmController.getId(),1,"id = 1");
    }

    @Test
    public void createNewFilmBadNameTest() {
        Film film = new Film (1, "","гонки", LocalDate.parse("06.06.2022", DateTimeFormatter.ofPattern("dd.MM.yyyy")), 90);
        FilmController filmController = new FilmController();
        try {
            filmController.validate (film);
        } catch (ValidationException e) {
            System.out.println("Проверка на пустое название фильма. Сообщение: ");
            ValidationException validationException = new ValidationException(e.getMessage());
            assertEquals(validationException.toString(),"Название не может быть пустым",
                    "Не верно указано название фильма");
        }
    }

    @Test
    public void createNewFilmDescriptionTest() {
        Film film = new Film (1, "Форсаж","гонки-гонки-гонки-гонки-гонки-гонки-гонки-гонки-гонки-гонки" +
                "-гонки-гонки-гонки-гонки-гонки-гонки-гонки-гонки-гонки-гонки-гонки-гонки-гонки-гонки-гонки-гонки-гонки-" +
                "гонки-гонки-гонки-гонки-гонки-гонки-гонки-гонки-гонки-гонки-",
                LocalDate.parse("06.06.2022", DateTimeFormatter.ofPattern("dd.MM.yyyy")), 90);
        FilmController filmController = new FilmController();
        try {
            filmController.validate (film);
        } catch (ValidationException e) {
            System.out.println("Проверка описания длинной > 200 символов. Сообщение: ");
            ValidationException validationException = new ValidationException(e.getMessage());
            assertEquals(validationException.toString(),"Длина описания сокращена до 200 символов",
                    "Длина описания сокращена до 200 символов");
        }
    }

    @Test
    public void createNewFilmNullDataTest() {
        Film film = new Film (1, "Форсаж","гонки", null, 90);
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
        Film film = new Film (5, "Форсаж","гонки", LocalDate.parse("27.12.1895", DateTimeFormatter.ofPattern("dd.MM.yyyy")), 90);
        FilmController filmController = new FilmController();
        try {
            filmController.validate (film);
        } catch (ValidationException e) {
            System.out.println("Проверка на не верную дату релиза. Сообщение: ");
            ValidationException validationException = new ValidationException(e.getMessage());
            assertEquals(validationException.toString(),"Дата релиза — не может быть раньше 1895-12-28",
                    "Дата релиза — не может быть раньше 1895-12-28");
        }
    }

    @Test
    public void createNewFilmBadDurationTest() {
        Film film = new Film (5, "Форсаж","гонки", LocalDate.parse("06.06.2022", DateTimeFormatter.ofPattern("dd.MM.yyyy")), -1);
        FilmController filmController = new FilmController();
        try {
            filmController.validate (film);
        } catch (ValidationException e) {
            ValidationException validationException = new ValidationException(e.getMessage());
            assertEquals(validationException.toString(),"Продолжительность фильма должна быть положительной.",
                    "Продолжительность фильма должна быть положительной.");
        }
    }
}
