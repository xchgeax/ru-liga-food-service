package ru.liga.service;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RabbitMQKitchenServiceImpl implements RabbitMQKitchenService {

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void sendOrderToDeliveryService(Long orderId) {
        rabbitTemplate.convertAndSend("directExchange", "delivery", orderId);
    }
}