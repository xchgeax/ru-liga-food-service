package ru.liga.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import ru.liga.dto.OrderStatusUpdateDto;
import ru.liga.exception.IncorrectOrderStateException;
import ru.liga.exception.ResourceNotFoundException;
import ru.liga.service.OrderService;

@Service
@Slf4j
@RequiredArgsConstructor
public class QueueListener {
    private final OrderService orderService;

    @RabbitListener(queues = "orderStatus")
    public void processUpdateOrderStatusQueue(String message) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        OrderStatusUpdateDto orderStatusUpdateDto = objectMapper.readValue(message, OrderStatusUpdateDto.class);

        log.info("Received new order to process id={}", orderStatusUpdateDto.getOrderId());

        try {
            orderService.updateOrderStatus(orderStatusUpdateDto.getOrderId(), orderStatusUpdateDto.getStatus());
        } catch (ResourceNotFoundException | IncorrectOrderStateException e) {
            log.warn("Order id={} wasn't processed successfully, reason={}",
                    orderStatusUpdateDto.getOrderId(),
                    e.getMessage());
        }
    }
}