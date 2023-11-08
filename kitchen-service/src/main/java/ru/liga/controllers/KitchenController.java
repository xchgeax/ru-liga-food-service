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
import ru.liga.exception.IncorrectOrderStateException;
import ru.liga.exception.ResourceNotFoundException;
import ru.liga.service.KitchenService;

import java.util.UUID;

@Tag(name = "API кухни для работы с заказами")
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/kitchen")
public class KitchenController {

    private final KitchenService kitchenService;

    @Operation(summary = "Принять заказ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK")
    })
    @PostMapping("/{id}/accept")
    public void acceptOrder(@PathVariable UUID id) {
        log.info("Received POST request to accept order by id {}", id);
        kitchenService.acceptOrder(id);
    }

    @Operation(summary = "Отменить заказ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK")
    })
    @PostMapping("/{id}/deny")
    public void denyOrder(@PathVariable UUID id) {
        log.info("Received POST request to deny order by id {}", id);
        kitchenService.denyOrder(id);
    }

    @Operation(summary = "Завершить заказ и отправить в доставку")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Order was not found"),
            @ApiResponse(responseCode = "400", description = "New order state is not acceptable")
    })
    @PostMapping("/{id}/ready")
    public void readyOrder(@PathVariable UUID id) throws ResourceNotFoundException, IncorrectOrderStateException {
        log.info("Received POST request to finish order by id {}", id);
        kitchenService.readyOrder(id);
    }
}
