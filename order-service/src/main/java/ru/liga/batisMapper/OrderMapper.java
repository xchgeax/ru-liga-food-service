package ru.liga.batisMapper;

import ru.liga.entity.Order;

import java.util.List;

public interface OrderMapper {
    Order getOrderById(int id);

    List<Order> getOrderByStatus(String status);

    void updateOrderStatus(Order order);
}
