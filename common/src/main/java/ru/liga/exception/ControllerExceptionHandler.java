package ru.liga.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.liga.entity.Order;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(value = ResourceNotFoundException.class)
    protected ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(value = NoOrderItemsSuppliedException.class)
    protected ResponseEntity<Object> handleNoOrderItemsSuppliedException(NoOrderItemsSuppliedException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(value = IncorrectOrderStateException.class)
    protected ResponseEntity<Object> handleIncorrectOrderStateException(IncorrectOrderStateException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(value = OrderAlreadyDeliveredException.class)
    protected ResponseEntity<Object> handleOrderAlreadyDeliveredException(OrderAlreadyDeliveredException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }
}
