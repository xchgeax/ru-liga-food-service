package ru.liga.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.liga.dto.UpdateStatusDto;
import ru.liga.dto.DeliveryDto;
import ru.liga.dto.DeliveryStatusConfirmationDto;
import ru.liga.entity.OrderStatus;
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

    @Operation(summary = "Update delivery status")
    @PostMapping("/status/{id}")
    public ResponseEntity<DeliveryStatusConfirmationDto> updateDeliveryStatus(@PathVariable("id") Long id,
                                                                              @RequestBody UpdateStatusDto statusDto) {
        return ResponseEntity.ok(deliveryService.updateDeliveryStatus(id, statusDto.getStatus()));
    }
}
