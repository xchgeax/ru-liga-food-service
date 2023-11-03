package ru.liga.service;

import ru.liga.dto.OrderItemConfirmationDto;
import ru.liga.entity.OrderItem;
import ru.liga.exception.ResourceNotFoundException;

import java.util.List;

public interface OrderItemService {

    OrderItemConfirmationDto saveNewOrderItem(Long orderId, Long menuItemId, Integer quantity) throws ResourceNotFoundException;

    void saveOrderItemList(List<OrderItem> orderItemList);

    void deleteOrderItem(Long id);
}
