package ru.liga.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.liga.dto.OrderConfirmationDto;
import ru.liga.dto.OrderCreationDto;
import ru.liga.dto.OrderDto;
import ru.liga.dto.OrderStatusUpdateDto;
import ru.liga.entity.OrderStatus;
import ru.liga.exception.NoOrderItemsSuppliedException;
import ru.liga.exception.ResourceNotFoundException;
import ru.liga.service.OrderService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Tag(name = "Orders management API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "Get all orders")
    @GetMapping
    public ResponseEntity<Page<OrderDto>> getOrderList(@PositiveOrZero @RequestParam Integer pageIndex,
                                                       @Positive @RequestParam Integer pageCount) {
        return ResponseEntity.ok(orderService.getOrderList(pageIndex, pageCount));
    }

    @Operation(summary = "Get order by ID")
    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable("id") Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @Operation(summary = "Get orders with specified status")
    @GetMapping("/status/{status}")
    public ResponseEntity<List<OrderDto>> getOrderListByStatus(@PathVariable OrderStatus status) {
        return ResponseEntity.ok(orderService.getOrderListByStatus(status));
    }

    @Operation(summary = "Create new order")
    @PostMapping
    public ResponseEntity<OrderConfirmationDto> createOrder(@RequestBody OrderCreationDto order) throws ResourceNotFoundException, NoOrderItemsSuppliedException {
        return ResponseEntity.ok(orderService.createOrder(order.getRestaurantId(), order.getMenuItems()));
    }

    @Operation(summary = "Update order status")
    @PostMapping("/{id}/status")
    public void updateOrderStatus(@PathVariable("id") Long id,
                                  @RequestBody OrderStatusUpdateDto updateDto) throws ResourceNotFoundException {
        orderService.updateOrderStatus(id, updateDto.getStatus());
    }

}
