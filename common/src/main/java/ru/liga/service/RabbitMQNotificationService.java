package ru.liga.service;

import java.util.UUID;

public interface RabbitMQNotificationService {

    void sendNotificationOnOrder(UUID orderId);
}
