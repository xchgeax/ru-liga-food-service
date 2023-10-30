package ru.liga.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidDeliveryStatusException extends Exception {

    public InvalidDeliveryStatusException() {
        super("Provided delivery status does not exist");
    }
}
