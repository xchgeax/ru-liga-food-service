package ru.liga.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import ru.liga.dto.OrderDto;

@Service
@Slf4j
@RequiredArgsConstructor
public class QueueListener {

    // TODO: process delivery-ready orders
    @SneakyThrows
    @RabbitListener(queues = "delivery")
    public void processDeliveryQueue(String orderInfo) {
        ObjectMapper objectMapper = new ObjectMapper();
        OrderDto orderDto = objectMapper.readValue(orderInfo, OrderDto.class);
        log.info(orderDto.toString());
    }
}
