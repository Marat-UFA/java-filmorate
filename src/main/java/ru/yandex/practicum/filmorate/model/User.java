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
@Builder
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

    public User(int user_id, String user_name, String login, String email, LocalDate birthday) {
        this.id = user_id;
        this.name = user_name;
        this.login = login;
        this.email = email;
        this.birthday = birthday;
    }

    public User(String user_name, String login, String email, LocalDate birthday) {
        this.name = user_name;
        this.login = login;
        this.email = email;
        this.birthday = birthday;
    }
}