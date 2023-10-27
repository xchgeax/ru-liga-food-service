package ru.liga.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.dto.OrderItemConfirmationDto;
import ru.liga.entity.Order;
import ru.liga.entity.OrderItem;
import ru.liga.entity.RestaurantMenuItem;
import ru.liga.exception.ResourceNotFoundException;
import ru.liga.repo.OrderItemRepository;
import ru.liga.repo.OrderRepository;
import ru.liga.repo.RestaurantMenuItemRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderItemService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final RestaurantMenuItemRepository restaurantMenuItemRepository;

    public OrderItemConfirmationDto saveNewOrderItem(Long orderId, Long menuItemId, Integer quantity) throws ResourceNotFoundException {
        Order order = orderRepository.findOrderById(orderId);
        RestaurantMenuItem restaurantMenuItem = restaurantMenuItemRepository.findRestaurantMenuItemById(menuItemId);

        if (restaurantMenuItem == null) throw new ResourceNotFoundException("Menu item not found");
        if (order == null) throw new ResourceNotFoundException("Order not found");

        OrderItem orderItem = OrderItem.builder()
                .order(order)
                .restaurantMenuItem(restaurantMenuItem)
                .quantity(quantity)
                .price(restaurantMenuItem.getPrice() * quantity)
                .build();
        orderItemRepository.save(orderItem);

        OrderItemConfirmationDto orderItemConfirmationDto = new OrderItemConfirmationDto();
        return orderItemConfirmationDto.setId(orderItem.getId());
    }

    public void saveOrderItemList(List<OrderItem> orderItemList) {
        orderItemRepository.saveAll(orderItemList);
    }


    public void deleteOrderItem(Long id) {
        orderItemRepository.deleteById(id);
    }
}
