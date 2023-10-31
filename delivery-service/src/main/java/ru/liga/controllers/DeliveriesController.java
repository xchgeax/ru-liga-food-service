package ru.liga.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.liga.dto.DeliveryDto;
import ru.liga.entity.OrderStatus;
import ru.liga.exception.IncorrectOrderStateException;
import ru.liga.exception.OrderAlreadyDeliveredException;
import ru.liga.exception.ResourceNotFoundException;
import ru.liga.service.DeliveryService;

import java.util.List;

@Tag(name = "Deliveries management API")
@RestController
@Slf4j
@RequestMapping("/delivery")
@RequiredArgsConstructor
public class DeliveriesController {

    private final DeliveryService deliveryService;

    @Operation(summary = "Get delivery list by status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/status")
    public ResponseEntity<List<DeliveryDto>> getDeliveryListByStatus(@RequestParam OrderStatus status) {
        return ResponseEntity.ok(deliveryService.getDeliveryListByStatus(status));
    }

    @Operation(summary = "Get delivery by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Order was not found")
    })
    @GetMapping("/id/{orderId}")
    public ResponseEntity<DeliveryDto> getDeliveryById(@PathVariable("orderId") Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(deliveryService.getDeliveryById(id));
    }

    @Operation(summary = "Pick delivery by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Kitchen is yet to prepare the order"),
            @ApiResponse(responseCode = "404", description = "Order was not found"),
            @ApiResponse(responseCode = "409", description = "Order is already delivered")
    })
    @PostMapping("/pick/{orderId}")
    public void pickDelivery(@PathVariable("orderId") Long id)
            throws IncorrectOrderStateException, ResourceNotFoundException, OrderAlreadyDeliveredException {
        log.info("Received POST request to pick delivery by id {}", id);
        deliveryService.pickDelivery(id);
    }

    @Operation(summary = "Complete delivery by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Order is yet to be picked up"),
            @ApiResponse(responseCode = "404", description = "Order was not found"),
            @ApiResponse(responseCode = "409", description = "Order is already delivered")
    })
    @PostMapping("/complete/{orderId}")
    public void completeDelivery(@PathVariable("orderId") Long id)
            throws IncorrectOrderStateException, ResourceNotFoundException, OrderAlreadyDeliveredException {
        log.info("Received POST request to complete delivery by id {}", id);
        deliveryService.completeDelivery(id);
    }

}
