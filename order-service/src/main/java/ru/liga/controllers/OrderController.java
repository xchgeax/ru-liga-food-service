package ru.liga.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.liga.dto.OrderConfirmationDto;
import ru.liga.dto.OrderCreationDto;
import ru.liga.dto.OrderDto;
import ru.liga.entity.OrderStatus;
import ru.liga.service.OrderService;

import java.util.List;

@Tag(name = "Orders management API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    // TODO: return page
    @Operation(summary = "Get all orders")
    @GetMapping("/")
    public ResponseEntity<List<OrderDto>> getOrderList() {
       return ResponseEntity.ok(orderService.getOrderList());
    }

    @Operation(summary = "Get order by ID")
    @GetMapping("/id/{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @Operation(summary = "Get orders with specified status")
    @GetMapping("/status/{status}")
    public ResponseEntity<List<OrderDto>> getOrderListByStatus(@PathVariable OrderStatus status) {
        return ResponseEntity.ok(orderService.getOrderListByStatus(status));
    }

    @Operation(summary = "Create new order")
    @PostMapping
    public ResponseEntity<OrderConfirmationDto> createOrder(@RequestBody OrderCreationDto order) {
        return ResponseEntity.ok(orderService.createOrder(order.getRestaurantId(), order.getMenuItems()));
    }

}
