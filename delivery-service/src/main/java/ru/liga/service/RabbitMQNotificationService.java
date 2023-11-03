package ru.liga.service;

public interface RabbitMQNotificationService {

    void sendCourierNotificationOnOrder(Long orderId);
}
