package ru.liga.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.dto.OrderStatusUpdateDto;
import ru.liga.entity.Order;
import ru.liga.entity.OrderStatus;
import ru.liga.exception.IncorrectOrderStateException;
import ru.liga.exception.ResourceNotFoundException;
import ru.liga.repo.OrderRepository;
import ru.liga.service.KitchenService;
import ru.liga.service.RabbitMQKitchenService;
import ru.liga.service.RabbitMQOrderService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class KitchenServiceImpl implements KitchenService {

    private final RabbitMQOrderService rabbitMQOrderService;
    private final RabbitMQKitchenService rabbitMQKitchenService;
    private final OrderRepository orderRepository;

    public void acceptOrder(UUID orderId) {
        OrderStatusUpdateDto orderStatusUpdateDto = new OrderStatusUpdateDto(orderId, OrderStatus.KITCHEN_ACCEPTED);
        rabbitMQOrderService.sendNewOrderStatus(orderStatusUpdateDto);
    }

    public void denyOrder(UUID orderId) {
        OrderStatusUpdateDto orderStatusUpdateDto = new OrderStatusUpdateDto(orderId, OrderStatus.KITCHEN_DENIED);
        rabbitMQOrderService.sendNewOrderStatus(orderStatusUpdateDto);
    }

    public void readyOrder(UUID orderId) throws ResourceNotFoundException, IncorrectOrderStateException {
        Order order = orderRepository.findById(orderId).orElseThrow(ResourceNotFoundException::new);

        if (order.getStatus() != OrderStatus.KITCHEN_ACCEPTED) throw new IncorrectOrderStateException();

        rabbitMQKitchenService.sendOrderToDeliveryService(orderId);
    }
}
