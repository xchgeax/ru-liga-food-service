package ru.liga.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.dto.DeliveryDto;
import ru.liga.dto.OrderStatusUpdateDto;
import ru.liga.entity.Order;
import ru.liga.entity.OrderStatus;
import ru.liga.mapper.DeliveryMapper;
import ru.liga.repo.OrderRepository;
import ru.liga.service.DeliveryService;
import ru.liga.service.RabbitMQOrderService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {

    private final OrderRepository orderRepository;
    private final DeliveryMapper deliveryMapper;
    private final RabbitMQOrderService rabbitMQOrderService;

    public List<DeliveryDto> getAvailableDeliveryList() {
        List<Order> orders = orderRepository.findOrderByStatus(OrderStatus.DELIVERY_PENDING);

        return deliveryMapper.orderToDeliveryDto(orders);
    }

    public void completeDelivery(UUID orderId) {
        OrderStatusUpdateDto orderStatusUpdateDto = new OrderStatusUpdateDto(orderId, OrderStatus.DELIVERY_COMPLETE);
        rabbitMQOrderService.sendNewOrderStatus(orderStatusUpdateDto);
    }

    public void takeDelivery(UUID orderId) {
        OrderStatusUpdateDto orderStatusUpdateDto = new OrderStatusUpdateDto(orderId, OrderStatus.DELIVERY_DELIVERING);
        rabbitMQOrderService.sendNewOrderStatus(orderStatusUpdateDto);
    }

}
