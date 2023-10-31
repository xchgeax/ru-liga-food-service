package ru.liga.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.liga.dto.DeliveryDto;
import ru.liga.entity.OrderStatus;
import ru.liga.exception.ResourceNotFoundException;
import ru.liga.service.DeliveryService;

import java.util.List;

@Tag(name = "Deliveries management API")
@RestController
@RequiredArgsConstructor
public class DeliveriesController {

    private final DeliveryService deliveryService;

    @Operation(summary = "Get delivery list by status")
    @GetMapping("/status")
    public ResponseEntity<List<DeliveryDto>> getDeliveryListByStatus(@RequestParam OrderStatus status) {
        return ResponseEntity.ok(deliveryService.getDeliveryListByStatus(status));
    }

    @Operation(summary = "Get delivery by id")
    @GetMapping("/{id}")
    public ResponseEntity<DeliveryDto> getDeliveryById(@PathVariable("id") Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(deliveryService.getDeliveryById(id));
    }

}
