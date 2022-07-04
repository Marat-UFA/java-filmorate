package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
    public class Film {

        Integer id;

        @NotBlank
        String name;

        String description;

        @Past
        LocalDate releaseDate;

        int duration;
    }
