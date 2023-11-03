package ru.liga.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.client.KitchenClient;
import ru.liga.dto.OrderStatusUpdateDto;
import ru.liga.entity.OrderStatus;
import ru.liga.exception.ResourceNotFoundException;
import ru.liga.repo.OrderRepository;
import ru.liga.service.KitchenService;
import ru.liga.service.RabbitMQKitchenService;

@Service
@RequiredArgsConstructor
public class KitchenServiceImpl implements KitchenService {

    private final KitchenClient kitchenClient;
    private final RabbitMQKitchenService rabbitMQKitchenService;
    private final OrderRepository orderRepository;

    public void acceptOrder(Long orderId) {
        OrderStatusUpdateDto orderStatusUpdateDto = new OrderStatusUpdateDto(OrderStatus.KITCHEN_ACCEPTED);
        kitchenClient.updateOrderStatus(orderId, orderStatusUpdateDto);
    }

    public void denyOrder(Long orderId) {
        OrderStatusUpdateDto orderStatusUpdateDto = new OrderStatusUpdateDto(OrderStatus.KITCHEN_DENIED);
        kitchenClient.updateOrderStatus(orderId, orderStatusUpdateDto);
    }

    public void finishOrder(Long orderId) throws ResourceNotFoundException {
        orderRepository.findById(orderId).orElseThrow(ResourceNotFoundException::new);
        rabbitMQKitchenService.sendOrderToDeliveryService(orderId);
    }
}
