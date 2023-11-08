package ru.liga.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import ru.liga.dto.OrderStatusUpdateDto;
import ru.liga.service.RabbitMQOrderService;

@Service
@RequiredArgsConstructor
public class RabbitMQOrderServiceImpl implements RabbitMQOrderService {

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void sendNewOrderStatus(OrderStatusUpdateDto orderStatusUpdateDto) {
        rabbitTemplate.convertAndSend("orderStatus", orderStatusUpdateDto);
    }
}
