package ru.liga.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.liga.dto.*;
import ru.liga.entity.*;
import ru.liga.repo.OrderItemRepository;
import ru.liga.repo.OrderRepository;
import ru.liga.repo.RestaurantMenuItemRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    OrderRepository orderRepository;
    OrderItemRepository orderItemRepository;
    RestaurantMenuItemRepository restaurantMenuItemRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository,
                        OrderItemRepository orderItemRepository,
                        RestaurantMenuItemRepository restaurantMenuItemRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.restaurantMenuItemRepository = restaurantMenuItemRepository;
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

    public OrderConfirmationDto createOrder(Long restaurantId, List<OrderItemDto> menuItems) {
        return new OrderConfirmationDto().setId(0L).setArrivalTime(123L).setPaymentUrl("google.com");
    }

    public OrderItemConfirmationDto saveNewOrderItem(Long orderId, Long menuItemId, int quantity) {

        OrderItemConfirmationDto orderItemConfirmationDto = new OrderItemConfirmationDto();
        RestaurantMenuItem restaurantMenuItem = restaurantMenuItemRepository.findRestaurantMenuItemById(menuItemId);
        Order order = orderRepository.findOrderById(orderId);

        if (restaurantMenuItem == null || order == null) return orderItemConfirmationDto.setId(-1L);

        OrderItem orderItem = OrderItem.builder()
                .order(order)
                .restaurantMenuItem(restaurantMenuItem)
                .quantity(quantity)
                .price(restaurantMenuItem.getPrice() * quantity)
                .build();
        orderItemRepository.save(orderItem);

        return orderItemConfirmationDto.setId(orderItem.getId());
    }

    public void deleteOrderItem(Long id) {
        OrderItem orderItem = orderItemRepository.findOrderItemById(id);

        if (orderItem == null) return;

        orderItemRepository.delete(orderItem);
    }
}
