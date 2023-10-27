package ru.liga.controllers;

import ru.liga.dto.OrderDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.liga.dto.*;
import ru.liga.entity.OrderStatus;
import ru.liga.exception.NoOrderItemsSuppliedException;
import ru.liga.exception.ResourceNotFoundException;
import ru.liga.service.OrderService;

import java.util.List;

@Tag(name = "Orders management API")
@RestController
@RequiredArgsConstructor
@RequestMapping
public class OrderController {

    private final OrderService orderService;

    // TODO: return page
    @Operation(summary = "Get all orders")
    @GetMapping
    public ResponseEntity<List<OrderDto>> getOrderList() {
        return ResponseEntity.ok(orderService.getOrderList());
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
    public ResponseEntity<Object> updateOrderStatus(@PathVariable("id") Long id,
                                                    @RequestBody OrderStatusUpdateDto updateDto) throws ResourceNotFoundException {
        orderService.updateOrderStatus(id, updateDto.getStatus());
        return ResponseEntity.ok().body(updateDto);
    }

    @Operation(summary = "Create new order item")
    @PostMapping("/{id}/items")
    public ResponseEntity<OrderItemConfirmationDto> saveNewOrderItem(@PathVariable("id") Long id,
                                                                     @RequestBody OrderItemDto orderItemDto) throws ResourceNotFoundException {
        return ResponseEntity.ok(orderService.saveNewOrderItem(id, orderItemDto.getMenuItemId(),
                orderItemDto.getQuantity()));
    }

    @Operation(summary = "Delete order item")
    @PostMapping("/item/delete/{id}")
    public void deleteOrderItem(@PathVariable("id") Long id) {
        orderService.deleteOrderItem(id);
    }

    @ExceptionHandler(value = ResourceNotFoundException.class)
    protected ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(value = NoOrderItemsSuppliedException.class)
    protected ResponseEntity<Object> handleNoOrderItemsSuppliedException(NoOrderItemsSuppliedException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
