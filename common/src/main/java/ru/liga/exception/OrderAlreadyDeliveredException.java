package ru.liga.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class OrderAlreadyDeliveredException extends Exception {

    public OrderAlreadyDeliveredException() {
        super("Order is already delivered");
    }
}
