package ru.liga.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.liga.dto.OrderConfirmationDto;
import ru.liga.dto.OrderDto;
import ru.liga.dto.OrderedItemDto;
import ru.liga.dto.RestaurantDto;
import ru.liga.entity.Order;
import ru.liga.entity.OrderStatus;
import ru.liga.repo.OrderRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public OrderDto getOrderById(Long id) {
        Order order = orderRepository.findOrderById(id);
        RestaurantDto restaurantDto = new RestaurantDto()
                .setName(order.getRestaurant().getName())
                .setAddress(order.getRestaurant().getAddress());
        return new OrderDto()
                .setId(order.getId())
                .setRestaurant(restaurantDto)
                .setTimestamp(order.getTimestamp());
    }

    public List<OrderDto> getOrderListByStatus(OrderStatus status) {
        return List.of(new OrderDto());
    }

    public List<OrderDto> getOrderList() {
        return List.of(new OrderDto());
    }

    public OrderConfirmationDto createOrder(Long restaurantId, List<OrderedItemDto> menuItems) {
        return new OrderConfirmationDto().setId(0L).setArrivalTime(123L).setPaymentUrl("google.com");
    }
}
