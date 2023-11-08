package ru.liga.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.liga.dto.OrderConfirmationDto;
import ru.liga.dto.OrderCreationDto;
import ru.liga.dto.OrderDto;
import ru.liga.entity.OrderStatus;
import ru.liga.exception.IncorrectOrderStateException;
import ru.liga.exception.NoOrderItemsSuppliedException;
import ru.liga.exception.ResourceNotFoundException;
import ru.liga.service.OrderService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.UUID;

@Tag(name = "API для управления заказами")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "Получение списка заказов")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK")
    })
    @GetMapping
    public ResponseEntity<Page<OrderDto>> getOrderList(@PositiveOrZero @RequestParam Integer pageIndex,
                                                       @Positive @RequestParam Integer pageCount) {
        return ResponseEntity.ok(orderService.getOrderList(pageIndex, pageCount));
    }

    @Operation(summary = "Получение заказа")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Order was not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable("id") UUID id) throws ResourceNotFoundException {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @Operation(summary = "Создание заказа")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Restaurant or customer was not found"),
            @ApiResponse(responseCode = "400", description = "No order items supplied")
    })
    @PostMapping
    public ResponseEntity<OrderConfirmationDto> createOrder(@RequestBody OrderCreationDto order)
            throws ResourceNotFoundException, NoOrderItemsSuppliedException {
        return ResponseEntity.ok(orderService.createOrder(order));
    }

    @Operation(summary = "Отмена заказа")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Order was not found"),
            @ApiResponse(responseCode = "400", description = "New order state is not acceptable")
    })
    @PostMapping("/{id}/cancel")
    public void cancelOrder(@PathVariable("id") UUID id)
            throws ResourceNotFoundException, IncorrectOrderStateException {
        orderService.cancelOrder(id);
    }

    @Operation(summary = "Обновление заказа")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Order was not found"),
            @ApiResponse(responseCode = "400", description = "New order state is not acceptable")
    })
    @PutMapping("/{id}")
    public void updateOrder(@PathVariable("id") UUID id, @RequestBody OrderStatus orderStatus)
            throws ResourceNotFoundException, IncorrectOrderStateException {
        orderService.updateOrderStatus(id, orderStatus);
    }

}
