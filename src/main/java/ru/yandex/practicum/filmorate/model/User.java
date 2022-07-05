package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDate;

@AllArgsConstructor
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
}