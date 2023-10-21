package ru.liga.clients;

import dto.OrderDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.liga.entity.OrderStatus;

import java.util.List;

@FeignClient(name = "orders-service", url = "http://localhost:8080/order")
public interface OrdersFeign {

    @GetMapping("/status/{status}")
    List<OrderDto> getOrderListByStatus(@PathVariable OrderStatus status);
}
