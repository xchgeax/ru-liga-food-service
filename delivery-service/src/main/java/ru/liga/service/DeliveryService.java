package ru.liga.service;

import lombok.RequiredArgsConstructor;
import ru.liga.dto.OrderDto;
import org.springframework.stereotype.Service;
import ru.liga.clients.OrdersFeign;
import ru.liga.dto.DeliveryDto;
import ru.liga.dto.DeliveryStatusConfirmationDto;
import ru.liga.entity.Order;
import ru.liga.entity.OrderStatus;
import ru.liga.exception.ResourceNotFoundException;
import ru.liga.mapper.DeliveryMapper;
import ru.liga.repo.OrderRepository;

import java.util.List;
import java.util.stream.Collectors;

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

    public DeliveryStatusConfirmationDto updateDeliveryStatus(Long id, OrderStatus status) throws ResourceNotFoundException {
        Order order = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Delivery not found"));

        order.setStatus(status);
        orderRepository.save(order);

        DeliveryStatusConfirmationDto statusConfirmationDto = new DeliveryStatusConfirmationDto();

        statusConfirmationDto.setId(id);
        statusConfirmationDto.setStatus(status);

        return statusConfirmationDto;
    }
}
