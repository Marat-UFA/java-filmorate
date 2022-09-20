package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.Exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Collection;


@RestController
@Slf4j
@RequestMapping("films")
public class FilmController {

    private int count = 0;

    @Autowired
    FilmService filmService;

    @PostMapping()
    Film saveFilm(@Valid @RequestBody Film film) throws ValidationException {
        validate(film);
        System.out.println(film);
        return filmService.save(film);
    }

    @PutMapping()
    Film updateFilm(@Valid @RequestBody Film film) throws ValidationException {
        validate(film);
        return filmService.update(film);
    }

    @GetMapping("/{filmId}")
    Film getFilm (@PathVariable int filmId){
        log.info("Get film by id={}",filmId);
        return filmService.getFilm(filmId);
    }

    @DeleteMapping("/{filmId}")
    public void deleteFilm (@PathVariable int filmId){
        log.info("delete film by id={}",filmId);
        filmService.deleteFilm(filmId);
    }
    @GetMapping("/popular")
    @ResponseBody
    Collection<Film> getFilmPopular (@RequestParam (defaultValue = "null") String count){
        if (count.equals("null")){
            return filmService.getFilmPopular(10);
        } else {
            return filmService.getFilmPopular(Integer.parseInt(count));
        }
    }

    @GetMapping()
    Collection<Film> getAllFilm (){
        return filmService.getAllFilm();
    }

    @PutMapping("/{filmId}/like/{userId}")
    public void addLike (@PathVariable int filmId, @PathVariable int userId){
        filmService.addLike(filmId, userId);
    }

    @DeleteMapping("/{filmId}/like/{userId}")
    public void deleteLike (@PathVariable int filmId, @PathVariable int userId){
        filmService.deleteLike(filmId, userId);
    }

    void validate (Film film) throws ValidationException {

        if (film.getReleaseDate() == null){
            throw new ValidationException("Дата не указана");
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1895,12,28))){
            throw new ValidationException("Дата релиза — не может быть раньше 1895-12-28");
        }
        if (film.getMpa()==null){
            throw new ValidationException("Не указан жанр");
        }
    }
}
