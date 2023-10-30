package ru.liga.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.dto.OrderItemConfirmationDto;
import ru.liga.dto.OrderItemCreationDto;
import ru.liga.entity.Order;
import ru.liga.entity.OrderItem;
import ru.liga.entity.RestaurantMenuItem;
import ru.liga.exception.NoOrderItemsSuppliedException;
import ru.liga.exception.ResourceNotFoundException;
import ru.liga.repo.OrderItemRepository;
import ru.liga.repo.OrderRepository;
import ru.liga.repo.RestaurantMenuItemRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderItemService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final RestaurantMenuItemRepository restaurantMenuItemRepository;
    private final RestaurantMenuItemRepository menuItemRepository;

    public OrderItemConfirmationDto saveNewOrderItem(Long orderId, Long menuItemId, Integer quantity) throws ResourceNotFoundException {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        RestaurantMenuItem restaurantMenuItem = restaurantMenuItemRepository.findById(menuItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Menu item not found"));

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

    public List<OrderItem> validateAndGetOrderedItems(Order order, Long restaurantId, List<OrderItemCreationDto> orderItemDtoList) throws NoOrderItemsSuppliedException {

        Map<Long, Integer> orderedMenuItemQuantity = orderItemDtoList.stream()
                .collect(Collectors.toMap(OrderItemCreationDto::getMenuItemId, OrderItemCreationDto::getQuantity));

        Set<Long> orderedMenuItemIds = orderedMenuItemQuantity.keySet();
        List<RestaurantMenuItem> menuItems = menuItemRepository.findAllByRestaurantIdAndIdIn(restaurantId, orderedMenuItemIds);

        if (menuItems.isEmpty()) throw new NoOrderItemsSuppliedException();

        List<OrderItem> orderedItems = new ArrayList<>();

        menuItems.forEach(menuItem -> {
            Integer quantity = orderedMenuItemQuantity.get(menuItem.getId());

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setRestaurantMenuItem(menuItem);
            orderItem.setQuantity(quantity);
            orderItem.setPrice(menuItem.getPrice() * quantity);

            orderedItems.add(orderItem);
        });

        return orderedItems;
    }

    public void saveOrderItemList(List<OrderItem> orderItemList) {
        orderItemRepository.saveAll(orderItemList);
    }


    public void deleteOrderItem(Long id) {
        orderItemRepository.deleteById(id);
    }
}
