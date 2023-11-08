package ru.liga.service;

import ru.liga.exception.IncorrectOrderStateException;
import ru.liga.exception.ResourceNotFoundException;

import java.util.UUID;

public interface KitchenService {

    void acceptOrder(UUID orderId);

    void denyOrder(UUID orderId);

    void readyOrder(UUID orderId) throws ResourceNotFoundException, IncorrectOrderStateException;
}
