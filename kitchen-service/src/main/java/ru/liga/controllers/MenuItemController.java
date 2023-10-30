package ru.liga.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.liga.dto.*;
import ru.liga.exception.ResourceNotFoundException;
import ru.liga.service.RestaurantService;

import java.util.List;

@Tag(name = "Menu item management API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/menu-item")
public class MenuItemController {

    private final RestaurantService restaurantService;

    @Operation(summary = "Find restaurant menu items by restaurant id")
    @GetMapping("/restaurant/{id}")
    public ResponseEntity<List<RestaurantMenuItemDto>> getMenuItemsByRestaurantId(@PathVariable("id") Long id) {
        return ResponseEntity.ok(restaurantService.findMenuItemsByRestaurantId(id));
    }

    @Operation(summary = "Update restaurant item price by id")
    @PatchMapping("/{id}/price")
    public ResponseEntity<UpdatePriceConfirmationDto> updateMenuItemPriceById(@PathVariable("id") Long id,
                                                                          @RequestBody UpdatePriceDto statusDto) {
        return ResponseEntity.ok(restaurantService.updatePrice(id, statusDto.getPrice()));
    }

    @Operation(summary = "Delete menu item by id")
    @PostMapping("/{id}/delete")
    public void deleteMenuItemById(@PathVariable("id") Long id) {
        restaurantService.deleteMenuItem(id);
    }

    @Operation(summary = "Create new menu item for restaurant by id")
    @PostMapping("/create")
    public ResponseEntity<SaveMenuItemConfirmationDto> createMenuItem(@RequestBody SaveMenuItemDto menuItemDto) throws ResourceNotFoundException {
        return ResponseEntity.ok(restaurantService.saveMenuItem(menuItemDto));
    }

    @ExceptionHandler(value = ResourceNotFoundException.class)
    protected ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}