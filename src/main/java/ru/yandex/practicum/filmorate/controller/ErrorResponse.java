package ru.yandex.practicum.filmorate.controller;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ErrorResponse {
    String message;
    public ErrorResponse(String message) {
        this.message=message;
    }
}