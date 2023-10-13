package ru.liga.orderservice.service;

import org.springframework.stereotype.Service;
import ru.liga.orderservice.dto.OrderConfirmationDto;
import ru.liga.orderservice.dto.OrderDto;
import ru.liga.orderservice.dto.OrderedItemDto;

import java.util.List;

@Service
public class OrderService {

    public OrderDto getOrderById(Long id) {
        return new OrderDto().setId(id);
    }

    public List<OrderDto> getOrderList() {
        return List.of(new OrderDto());
    }

    public OrderConfirmationDto createOrder(Long restaurantId, List<OrderedItemDto> menuItems) {
        return new OrderConfirmationDto().setId(0L).setArrivalTime(123L).setPaymentUrl("google.com");
    }
}
