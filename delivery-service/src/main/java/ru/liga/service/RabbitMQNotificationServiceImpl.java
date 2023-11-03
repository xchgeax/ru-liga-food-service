package ru.liga.service;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RabbitMQNotificationServiceImpl implements RabbitMQNotificationService {

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void sendCourierNotificationOnOrder(Long orderId) {
        rabbitTemplate.convertAndSend("courierNotification", orderId);
    }
}
