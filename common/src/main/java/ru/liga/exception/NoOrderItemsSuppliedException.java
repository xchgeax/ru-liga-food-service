package ru.liga.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class NoOrderItemsSuppliedException extends Exception {

    public NoOrderItemsSuppliedException() {
        super("Order items weren't supplied or are not valid");
    }
}
