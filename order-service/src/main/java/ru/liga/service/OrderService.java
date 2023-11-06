package ru.liga.service;

import org.springframework.data.domain.Page;
import ru.liga.dto.OrderConfirmationDto;
import ru.liga.dto.OrderCreationDto;
import ru.liga.dto.OrderDto;
import ru.liga.dto.OrderItemCreationDto;
import ru.liga.entity.OrderStatus;
import ru.liga.exception.NoOrderItemsSuppliedException;
import ru.liga.exception.ResourceNotFoundException;

import java.util.List;

public interface OrderService {

    OrderDto getOrderById(Long id) throws ResourceNotFoundException;

    List<OrderDto> getOrderListByStatus(OrderStatus status);

    Page<OrderDto> getOrderList(Integer pageIndex, Integer pageCount);

    OrderConfirmationDto createOrder(OrderCreationDto orderCreationDto) throws ResourceNotFoundException, NoOrderItemsSuppliedException;

    void updateOrderStatus(Long id, OrderStatus status) throws ResourceNotFoundException;
}
