package ru.yandex.practicum.filmorate.Exception;

public class ValidationException extends Exception{
    public ValidationException(final String message) {
        super(message);
    }
    public ValidationException() {
    }
    @Override
    public String toString() {
        return getMessage();
    }
}
