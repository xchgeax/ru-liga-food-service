package ru.liga.service;

import java.util.UUID;

public interface RabbitMQKitchenService {

    void sendOrderToDeliveryService(UUID orderId);

}
