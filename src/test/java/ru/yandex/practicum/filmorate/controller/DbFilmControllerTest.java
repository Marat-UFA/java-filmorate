package ru.yandex.practicum.filmorate.controller;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.Exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.DbFilmStorage;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@AutoConfigureTestDatabase
public class DbFilmControllerTest {

    private DbFilmStorage DbfilmStorage;

    @Autowired
    public DbFilmControllerTest(DbFilmStorage DbfilmStorage) {
        this.DbfilmStorage = DbfilmStorage;
    }

    private Film createFilm_1() {
        Film film = new Film( 0,"New",
                "Фильм изображает будущее",
                LocalDate.of(2019, 10, 14),
                136, new Mpa(2,null),0);
        return film;
    }

    private Film createFilm_2() {
        Film film = new Film(0,"Film_2",
                "Film_2",
                LocalDate.of(2020, 8, 25),
                150, new Mpa(1,null),0);
        return film;
    }

    @Test
    void findFilmByIdTest() {
        Film film = DbfilmStorage.save(createFilm_1());
        Optional<Film> optionalFilm = Optional.ofNullable(DbfilmStorage.get(film.getId()));
        assertNotNull(optionalFilm);
        assertThat(optionalFilm)
                .isPresent()
                .hasValueSatisfying(u ->
                        assertThat(u).hasFieldOrPropertyWithValue("name", "New"));
        DbfilmStorage.deleteFilm(film.getId());
    }

    @Test
    void updateFilmTest() {
        Film filmUpdate = createFilm_1();
        DbfilmStorage.save(filmUpdate);
        Film filmUpdate2 = createFilm_2();
        filmUpdate2.setId(filmUpdate.getId());
        DbfilmStorage.update(filmUpdate2);
        Optional<Film> filmOptional = Optional.ofNullable(DbfilmStorage.get(filmUpdate.getId()));
        assertNotNull(filmOptional);
        assertThat(filmOptional)
                .isPresent()
                .hasValueSatisfying(u ->
                        assertThat(u).hasFieldOrPropertyWithValue("name", "Film_2")
                );
        DbfilmStorage.deleteFilm(filmUpdate2.getId());
    }

    @Test
    void deleteFilmTest() {
        Film filmDelete = createFilm_1();
        DbfilmStorage.save(filmDelete);
        Integer filmDeleteId = filmDelete.getId();
        DbfilmStorage.deleteFilm(filmDeleteId);
        assertThrows(NotFoundException.class, () -> DbfilmStorage.get(filmDeleteId));
    }


}
