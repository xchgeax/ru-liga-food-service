package ru.liga.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class IncorrectOrderStateException extends Exception {

    public IncorrectOrderStateException(String message) {
        super(message);
    }
}
