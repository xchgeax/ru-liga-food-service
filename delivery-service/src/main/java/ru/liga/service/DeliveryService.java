package ru.liga.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.clients.OrdersFeign;
import ru.liga.dto.DeliveryDto;
import ru.liga.dto.OrderDto;
import ru.liga.entity.Order;
import ru.liga.entity.OrderStatus;
import ru.liga.exception.IncorrectOrderStateException;
import ru.liga.exception.OrderAlreadyDeliveredException;
import ru.liga.exception.ResourceNotFoundException;
import ru.liga.mapper.DeliveryMapper;
import ru.liga.repo.OrderRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final OrdersFeign ordersFeign;
    private final DeliveryMapper deliveryMapper;
    private final OrderRepository orderRepository;

    public DeliveryDto getDeliveryById(Long Id) throws ResourceNotFoundException {
        Order order = orderRepository.findById(Id).orElseThrow(() -> new ResourceNotFoundException("Delivery not found"));

        return deliveryMapper.orderToDeliveryDto(order);
    }

    public List<DeliveryDto> getDeliveryListByStatus(OrderStatus status) {
        List<OrderDto> orders = ordersFeign.getOrderListByStatus(status);

        return deliveryMapper.orderDtoToDeliveryDto(orders);
    }

    public void completeDelivery(Long id) throws IncorrectOrderStateException, ResourceNotFoundException, OrderAlreadyDeliveredException {
        Order order = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Delivery not found"));

        checkOrderIsCompleted(order);
        if (!order.getStatus().equals(OrderStatus.DELIVERY_DELIVERING))
            throw new IncorrectOrderStateException("Order is not yet picked up");

        order.setStatus(OrderStatus.DELIVERY_COMPLETE);
        orderRepository.save(order);
    }

    public void pickDelivery(Long id) throws ResourceNotFoundException, IncorrectOrderStateException, OrderAlreadyDeliveredException {
        Order order = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Delivery not found"));

        checkOrderIsCompleted(order);
        if (!order.getStatus().equals(OrderStatus.DELIVERY_PICKING))
            throw new IncorrectOrderStateException("Order is not yet ready");

        order.setStatus(OrderStatus.DELIVERY_DELIVERING);
        orderRepository.save(order);
    }

    private void checkOrderIsCompleted(Order order) throws OrderAlreadyDeliveredException {
        if (order.getStatus().equals(OrderStatus.DELIVERY_COMPLETE))
            throw new OrderAlreadyDeliveredException();
    }

}
