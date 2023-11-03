package ru.liga.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.liga.exception.ResourceNotFoundException;
import ru.liga.service.KitchenService;

@Tag(name = "Kitchen order management API")
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/kitchen")
public class KitchenController {

    private final KitchenService kitchenService;

    @Operation(summary = "Accept order by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Order was not found")
    })
    @PostMapping("/accept/{orderId}")
    public void acceptOrder(@PathVariable Long orderId) {
        log.info("Received POST request to accept order by id {}", orderId);
        kitchenService.acceptOrder(orderId);
    }

    @Operation(summary = "Deny order by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Order was not found")
    })
    @PostMapping("/deny/{orderId}")
    public void denyOrder(@PathVariable Long orderId) {
        log.info("Received POST request to deny order by id {}", orderId);
        kitchenService.denyOrder(orderId);
    }

    @Operation(summary = "Finish order and pass it to delivery")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Order was not found")
    })
    @PostMapping("/finish/{orderId}")
    public void finishOrder(@PathVariable Long orderId) throws ResourceNotFoundException {
        log.info("Received POST request to finish order by id {}", orderId);
        kitchenService.finishOrder(orderId);
    }
}
