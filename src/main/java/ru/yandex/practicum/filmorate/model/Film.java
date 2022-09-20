package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Data
@RequiredArgsConstructor
    public class Film {

    private Integer id;

    @NotNull(message = "Название фильма не может быть null")
    @NotBlank(message = "Название фильма не может быть пустым.")
    private String name;

    @NotNull
    @NotBlank
    @NotEmpty
    @Size (min = 1, max = 200)
    private String description;

    @Past
    private LocalDate releaseDate;

    @Min(1)
    private int duration;

    @JsonIgnore
    private Set<Integer> likeList = new HashSet<>();

    private List<Genre> genres;

    private Mpa mpa;


    public int getRating() {
        return 0;
    }

    private int rating = 0;

    public Film(int Film_id, String Film_name, String description, LocalDate releaseDate, int duration, Mpa mpa,
                int rating) {
        this.id = Film_id;
        this.name = Film_name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.mpa = mpa;
        this.rating = rating;
    }
//    public Film(String Film_name, String description, LocalDate releaseDate, int duration, Mpa mpa,
//                int rating) {
//        this.name = Film_name;
//        this.description = description;
//        this.releaseDate = releaseDate;
//        this.duration = duration;
//        this.mpa = mpa;
//        this.rating = rating;
//    }
}


