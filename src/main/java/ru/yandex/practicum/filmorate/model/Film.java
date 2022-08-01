package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@AllArgsConstructor
@Data
@NoArgsConstructor
    public class Film {

    private Integer id;

    @NotNull
    @NotBlank
    @NotEmpty
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
    Set<Integer> userID = new HashSet<>();

    }


