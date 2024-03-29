package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.FilmService;
import java.util.List;

@RestController
@Slf4j
public class MpaGenreController {

    private FilmService filmService;

    @Autowired
    public MpaGenreController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping("/mpa/{id}")
    public Mpa getMpaById(@PathVariable int id) {
        log.info("Get mpa by id ={}", id);
        return filmService.getMpaById(id);
    }

    @GetMapping("/genres/{id}")
    public Genre getGenreById(@PathVariable int id) {
        log.info("Get genre by id ={}", id);
        return filmService.getGenreById(id);
    }

    @GetMapping("/mpa")
    public List<Mpa> getAllMpa() {
        log.info("Get all mpa");
        return filmService.getAllMpa();
    }

    @GetMapping("/genres")
    public List<Genre> getAllGenres() {
        log.info("Get all genres");
        return filmService.getAllGenres();
    }

    @GetMapping("/genres/films")
    List<Film> getAllFilm (){
        log.info("Get film all with genre");
        return filmService.getAllFilm();
    }


}