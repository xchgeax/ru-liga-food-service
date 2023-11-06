package ru.liga.batisMapper;

import org.apache.ibatis.annotations.Mapper;
import ru.liga.entity.Order;

import java.util.List;

@Mapper
public interface OrderMapper {
    Order getOrderById(int id);

    List<Order> getOrderByStatus(String status);

    void updateOrderStatus(Order order);
}
