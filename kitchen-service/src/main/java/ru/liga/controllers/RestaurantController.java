package ru.liga.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.liga.dto.SaveMenuItemConfirmationDto;
import ru.liga.dto.SaveMenuItemDto;
import ru.liga.dto.UpdatePriceConfirmationDto;
import ru.liga.dto.UpdatePriceDto;
import ru.liga.exception.ResourceNotFoundException;
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
                                                                           @RequestBody SaveMenuItemDto menuItemDto) throws ResourceNotFoundException {
        return ResponseEntity.ok(restaurantService.saveMenuItem(id, menuItemDto));
    }

    @Operation(summary = "Delete menu item")
    @PostMapping("/item/delete/{id}")
    public void deleteMenuItem(@PathVariable("id") Long id) {
        restaurantService.deleteMenuItem(id);
    }

    @ExceptionHandler(value = ResourceNotFoundException.class)
    protected ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
