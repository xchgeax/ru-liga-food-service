package ru.liga.service;

import ru.liga.exception.ResourceNotFoundException;

public interface KitchenService {

    void acceptOrder(Long orderId);

    void denyOrder(Long orderId);

    void finishOrder(Long orderId) throws ResourceNotFoundException;
}
