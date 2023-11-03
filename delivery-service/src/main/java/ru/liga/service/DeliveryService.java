package ru.liga.service;

import ru.liga.dto.DeliveryDto;
import ru.liga.entity.OrderStatus;
import ru.liga.exception.IncorrectOrderStateException;
import ru.liga.exception.OrderAlreadyDeliveredException;
import ru.liga.exception.ResourceNotFoundException;

import java.util.List;

public interface DeliveryService {

    DeliveryDto getDeliveryById(Long Id) throws ResourceNotFoundException;

    List<DeliveryDto> getDeliveryListByStatus(OrderStatus status);

    void completeDelivery(Long id) throws IncorrectOrderStateException, ResourceNotFoundException, OrderAlreadyDeliveredException;

    void pickDelivery(Long id) throws ResourceNotFoundException, IncorrectOrderStateException, OrderAlreadyDeliveredException;
}
