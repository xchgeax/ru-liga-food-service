package ru.liga.service;

import ru.liga.exception.IncorrectOrderStateException;
import ru.liga.exception.ResourceNotFoundException;

import java.util.UUID;

public interface PaymentService {

    void pay(UUID orderId) throws ResourceNotFoundException, IncorrectOrderStateException;
}
