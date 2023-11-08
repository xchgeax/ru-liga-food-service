package ru.liga.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.liga.dto.DeliveryDto;
import ru.liga.service.DeliveryService;

import java.util.List;
import java.util.UUID;

@Tag(name = "API для работы доставки")
@RestController
@Slf4j
@RequestMapping("/api/delivery")
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;

    @Operation(summary = "Список доступных заказов")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
    })
    @GetMapping()
    public List<DeliveryDto> getAvailableDeliveryList() {
        log.info("Received GET request to get available delivery list");
        return deliveryService.getAvailableDeliveryList();
    }

    @Operation(summary = "Принять заказ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK")
    })
    @PostMapping("/{id}/take")
    public void takeDelivery(@PathVariable("id") UUID id) {
        log.info("Received POST request to pick delivery by id {}", id);
        deliveryService.takeDelivery(id);
    }

    @Operation(summary = "Завершить доставку")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK")
    })
    @PostMapping("/{id}/complete")
    public void completeDelivery(@PathVariable("id") UUID id) {
        log.info("Received POST request to complete delivery by id {}", id);
        deliveryService.completeDelivery(id);
    }

}
