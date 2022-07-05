package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.Exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@RestController
@Slf4j
public class FilmController {

    private final List<Film> films = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;

    @PostMapping(value = "/films")
    Film saveFilm(@Valid @RequestBody Film film) throws ValidationException {
        setId(id+1);
        film.setId(getId());
        validate(film);
        log.info("Фильм добавлен");
        films.add(film);
        return film;
    }
    @PutMapping(value = "/films")
    Film updateFilm(@Valid @RequestBody Film film) throws ValidationException {
        validate(film);
        for (int i=0; i < films.size(); i++){
            if (films.get(i).getId() == film.getId()){
                films.get(i).setName(film.getName());
                films.get(i).setDescription(film.getDescription());
                films.get(i).setReleaseDate(film.getReleaseDate());
                films.get(i).setDuration(film.getDuration());
                log.info("Фильм обновлен");
                return film;
            }
        }
        films.add(film);
        return film;
    }

    @GetMapping("/films")
    public List<Film> findAll() {
        log.debug("Текущее количество фильмов: {}", films.size());
        return films;
    }

    void validate (Film film) throws ValidationException {

        if (film.getId()==null) {
            throw new ValidationException("id пустой");
        }

        if (film.getId()<0){
            throw new ValidationException("id < 0");
        }

        if (film.getReleaseDate() == null){
            throw new ValidationException("Дата не указана");
        }

        if (film.getReleaseDate().isBefore(LocalDate.of(1895,12,28))){
            throw new ValidationException("Дата релиза — не может быть раньше 1895-12-28");
        }

    }

}
