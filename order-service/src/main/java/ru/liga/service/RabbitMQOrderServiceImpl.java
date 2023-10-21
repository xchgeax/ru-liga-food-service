package ru.liga.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.liga.dto.OrderDto;

@Service
public class RabbitMQOrderServiceImpl implements RabbitMQOrderService {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public RabbitMQOrderServiceImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public void sendOrderToDeliveryService(OrderDto order) {
        rabbitTemplate.convertAndSend("directExchange", "delivery", tryToSerializeMessageAsString(order));
    }

    private String tryToSerializeMessageAsString(OrderDto orderDto) {
        String orderInfo = null;
        try {
            orderInfo = objectMapper.writeValueAsString(orderDto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return orderInfo;
    }
}
