package ru.liga.notificationservice.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import ru.liga.notificationservice.dto.DeliveryDto;
import ru.liga.notificationservice.dto.UpdateStatusDto;

import java.util.List;

@Tag(name = "Deliveries management API")
@RestController
@RequestMapping("/deliveries")
public class DeliveriesController {

    // TODO: return page
    /**
     * @param status - active/complete delivery status
     */
    @Operation(summary = "Get delivery list by status")
    @GetMapping("/")
    public List<DeliveryDto> getDeliveryListByStatus(@RequestParam String status) {
        return List.of(new DeliveryDto());
    }

    @Operation(summary = "Update delivery status")
    @PostMapping("/{id}")
    public String updateDeliveryStatus(@PathVariable("id") Long id, @RequestBody UpdateStatusDto statusDto) {
        return "Delivery status has been updated";
    }
}
