package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    private int id;

    @NotNull
    @NotBlank
    @NotEmpty
    @Email
    @Min(1)
    private String email;

    @NotNull
    @NotBlank
    @NotEmpty
    @Min(1)
    @Pattern(regexp = "^\\S*$")
    private String login;

    @NotNull
    @NotBlank
    @NotEmpty
    private String name;

    @Past
    private LocalDate birthday;


    @JsonIgnore
    Set<Integer> friendID = new HashSet<>();

}