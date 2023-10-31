package ru.liga.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.liga.dto.OrderStatusUpdateDto;

@FeignClient(name = "orders-service", url = "http://localhost:8080/order")
public interface KitchenClient {

    @PostMapping("/{id}/status")
    void updateOrderStatus(@PathVariable("id") Long id,
                           @RequestBody OrderStatusUpdateDto updateDto);
}
