package ru.liga.service;

import ru.liga.dto.OrderDto;

public interface RabbitMQOrderService {

    void sendOrderToDeliveryService(OrderDto order);

}
