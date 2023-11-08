package ru.liga.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import ru.liga.service.RabbitMQNotificationService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RabbitMQNotificationServiceImpl implements RabbitMQNotificationService {

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void sendNotificationOnOrder(UUID orderId) {
        rabbitTemplate.convertAndSend("notification", orderId);
    }

}
