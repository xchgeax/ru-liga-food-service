package ru.liga.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.liga.dto.OrderItemConfirmationDto;
import ru.liga.dto.OrderItemDto;
import ru.liga.exception.ResourceNotFoundException;
import ru.liga.service.OrderItemService;

@Tag(name = "Order items management API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/order-item")
public class OrderItemController {

    private final OrderItemService orderItemService;

    @Operation(summary = "Create new order item")
    @PostMapping("/{id}/items")
    public ResponseEntity<OrderItemConfirmationDto> saveNewOrderItem(@PathVariable("id") Long id,
                                                                     @RequestBody OrderItemDto orderItemDto) throws ResourceNotFoundException {
        return ResponseEntity.ok(orderItemService.saveNewOrderItem(id, orderItemDto.getMenuItemId(),
                orderItemDto.getQuantity()));
    }

    @Operation(summary = "Delete order item")
    @PostMapping("/item/delete/{id}")
    public void deleteOrderItem(@PathVariable("id") Long id) {
        orderItemService.deleteOrderItem(id);
    }
}
