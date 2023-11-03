package ru.liga.service;

public interface RabbitMQKitchenService {

    void sendOrderToDeliveryService(Long orderId);

}
