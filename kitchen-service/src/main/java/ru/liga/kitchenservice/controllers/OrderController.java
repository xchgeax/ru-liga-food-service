package ru.liga.kitchenservice.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.liga.kitchenservice.dto.OrderDto;

import java.util.List;

@Tag(name = "Restaurant orders management API")
@RestController
@RequestMapping("/orders")
public class OrderController {

    // TODO: return page
    /**
     * @param status - active/complete/denied order status
     */
    @Operation(summary = "Get orders with specified status")
    @GetMapping("/")
    public List<OrderDto> getOrderListByStatus(@RequestParam String status) {
        return List.of(new OrderDto());
    }

}
