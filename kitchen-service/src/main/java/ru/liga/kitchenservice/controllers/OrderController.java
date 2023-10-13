package ru.liga.kitchenservice.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.liga.kitchenservice.dto.OrderDto;
import ru.liga.kitchenservice.service.OrderService;

import java.util.List;

@Tag(name = "Restaurant orders management API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    // TODO: return page
    /**
     * @param status - active/complete/denied order status
     */
    @Operation(summary = "Get orders with specified status")
    @GetMapping("/")
    public ResponseEntity<List<OrderDto>> getOrderListByStatus(@RequestParam String status) {
        return ResponseEntity.ok(orderService.getOrderListByStatus(status));
    }

}
