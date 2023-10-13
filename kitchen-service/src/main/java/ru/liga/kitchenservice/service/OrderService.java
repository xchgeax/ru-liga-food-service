package ru.liga.kitchenservice.service;

import org.springframework.stereotype.Service;
import ru.liga.kitchenservice.dto.OrderDto;

import java.util.List;

@Service
public class OrderService {

    public List<OrderDto> getOrderListByStatus(String status) {
        return List.of(new OrderDto());
    }
}
