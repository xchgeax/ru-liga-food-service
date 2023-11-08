package ru.liga.service;

import ru.liga.dto.DeliveryDto;

import java.util.List;
import java.util.UUID;

public interface DeliveryService {

    List<DeliveryDto> getAvailableDeliveryList();

    void completeDelivery(UUID id);

    void takeDelivery(UUID id);
}
