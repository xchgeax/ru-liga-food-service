package ru.liga.service;

import org.springframework.data.domain.Page;
import ru.liga.dto.OrderConfirmationDto;
import ru.liga.dto.OrderCreationDto;
import ru.liga.dto.OrderDto;
import ru.liga.entity.Order;
import ru.liga.entity.OrderStatus;
import ru.liga.exception.IncorrectOrderStateException;
import ru.liga.exception.NoOrderItemsSuppliedException;
import ru.liga.exception.ResourceNotFoundException;

import java.util.List;
import java.util.UUID;

public interface OrderService {

    OrderDto getOrderById(UUID id) throws ResourceNotFoundException;

    Page<OrderDto> getOrderList(Integer pageIndex, Integer pageCount);

    OrderConfirmationDto createOrder(OrderCreationDto orderCreationDto) throws ResourceNotFoundException, NoOrderItemsSuppliedException;

    void cancelOrder(UUID id) throws ResourceNotFoundException, IncorrectOrderStateException;

    void updateOrderStatus(UUID orderId, OrderStatus orderStatus) throws ResourceNotFoundException, IncorrectOrderStateException;
}
