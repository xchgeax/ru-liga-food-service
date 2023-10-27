package ru.liga.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.liga.dto.*;
import ru.liga.entity.RestaurantStatus;
import ru.liga.exception.ResourceNotFoundException;
import ru.liga.service.RestaurantService;

import java.util.List;

@Tag(name = "Restaurant management API")
@RestController
@RequiredArgsConstructor
@RequestMapping
public class RestaurantController {

    private final RestaurantService restaurantService;

    @Operation(summary = "Find restaurants by status")
    @GetMapping("/status/{status}")
    public ResponseEntity<List<RestaurantDto>> findRestaurantsByStatus(@PathVariable("status") RestaurantStatus status) {
        return ResponseEntity.ok(restaurantService.findRestaurantsByStatus(status));
    }

    @Operation(summary = "Find restaurant menu items by restaurant id")
    @GetMapping("/{id}/items")
    public ResponseEntity<List<RestaurantMenuItemDto>> getMenuItemsByRestaurantId(@PathVariable("id") Long id) {
        return ResponseEntity.ok(restaurantService.findMenuItemsByRestaurantId(id));
    }

    @Operation(summary = "Update restaurant item price")
    @PatchMapping("/item/{id}")
    public ResponseEntity<UpdatePriceConfirmationDto> updateMenuItemPrice(@PathVariable("id") Long id,
                                                                          @RequestBody UpdatePriceDto statusDto) {
        return ResponseEntity.ok(restaurantService.updatePrice(id, statusDto.getPrice()));
    }

    @Operation(summary = "Create new menu item")
    @PostMapping("/{id}/menu/create")
    public ResponseEntity<SaveMenuItemConfirmationDto> createMenuItem(@PathVariable("id") Long id,
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
