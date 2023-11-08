package ru.liga.service;

import ru.liga.dto.OrderStatusUpdateDto;

public interface RabbitMQOrderService {

    void sendNewOrderStatus(OrderStatusUpdateDto orderStatusUpdateDto);
}
