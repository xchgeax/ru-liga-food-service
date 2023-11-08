package ru.liga.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class IncorrectOrderStateException extends Exception {

    public IncorrectOrderStateException() {
        super("New order state is not acceptable for the order at it's current state");
    }
}
