package ru.liga.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.liga.dto.SaveMenuItemConfirmationDto;
import ru.liga.dto.SaveMenuItemDto;
import ru.liga.dto.UpdatePriceConfirmationDto;
import ru.liga.dto.UpdatePriceDto;
import ru.liga.service.RestaurantService;

@Tag(name = "Restaurant management API")
@RestController
@RequiredArgsConstructor
@RequestMapping
public class RestaurantController {

    private final RestaurantService restaurantService;

    @Operation(summary = "Update restaurant item price")
    @PostMapping("/item/{id}")
    public ResponseEntity<UpdatePriceConfirmationDto> updateMenuItemPrice(@PathVariable("id") Long id,
                                                                           @RequestBody UpdatePriceDto statusDto) {
        return ResponseEntity.ok(restaurantService.updatePrice(id, statusDto.getPrice()));
    }

    @Operation(summary = "Create new menu item")
    @PostMapping("/{id}/menu/create")
    public ResponseEntity<SaveMenuItemConfirmationDto> updateMenuItemPrice(@PathVariable("id") Long id,
                                                                           @RequestBody SaveMenuItemDto menuItemDto) {
        return ResponseEntity.ok(restaurantService.saveMenuItem(
                menuItemDto.getName(),
                menuItemDto.getPrice(),
                menuItemDto.getDescription(),
                menuItemDto.getImage(),
                id));
    }

    @Operation(summary = "Delete menu item")
    @PostMapping("/item/delete/{id}")
    public void deleteMenuItem(@PathVariable("id") Long id) {
        restaurantService.deleteMenuItem(id);
    }
}
