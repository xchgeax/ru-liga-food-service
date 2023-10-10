package ru.liga.orderservice.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import ru.liga.orderservice.dto.OrderConfirmationDto;
import ru.liga.orderservice.dto.OrderCreationDto;
import ru.liga.orderservice.dto.OrderDto;

import java.util.List;

@Tag(name = "Orders management API")
@RestController
@RequestMapping("/orders")
public class OrderController {

    // TODO: return page
    @Operation(summary = "Get all orders")
    @GetMapping("/")
    public List<OrderDto> getOrderList() {
       return List.of(new OrderDto());
    }

    @Operation(summary = "Get order by ID")
    @GetMapping("/{id}")
    public OrderDto getOrderById(@PathVariable("id") Long id) {
        return new OrderDto()
                .setId(id);
    }

    @Operation(summary = "Create new order")
    @PostMapping
    public OrderConfirmationDto newOrder(@RequestBody OrderCreationDto order) {
        return new OrderConfirmationDto().setId(0L).setArrivalTime(123L).setPaymentUrl("google.com");
    }

}
