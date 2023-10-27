package ru.liga.service;

public interface RabbitMQOrderService {

    void sendOrderToDeliveryService(Long orderId);

}
