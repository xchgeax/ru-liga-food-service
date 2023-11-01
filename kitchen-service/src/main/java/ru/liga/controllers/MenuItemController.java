package ru.liga.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.liga.dto.RestaurantMenuItemDto;
import ru.liga.dto.SaveMenuItemDto;
import ru.liga.dto.UpdatePriceDto;
import ru.liga.exception.ResourceNotFoundException;
import ru.liga.service.MenuItemService;

import java.util.List;

@Tag(name = "Menu item management API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/menu-item")
public class MenuItemController {

    private final MenuItemService menuItemService;

    @Operation(summary = "Find restaurant menu items by restaurant id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK")
    })
    @GetMapping("/restaurant/{id}")
    public ResponseEntity<List<RestaurantMenuItemDto>> getMenuItemsByRestaurantId(@PathVariable("id") Long id) {
        return ResponseEntity.ok(menuItemService.findMenuItemsByRestaurantId(id));
    }

    @Operation(summary = "Update restaurant item price by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            schema = @Schema(implementation = RestaurantMenuItemDto.class))),
            @ApiResponse(responseCode = "404", description = "Menu item was not found")
    })
    @PatchMapping("/{id}/price")
    public ResponseEntity<RestaurantMenuItemDto> updateMenuItemPriceById(@PathVariable("id") Long id,
                                                                         @RequestBody UpdatePriceDto statusDto)
            throws ResourceNotFoundException {
        return ResponseEntity.ok(menuItemService.updatePrice(id, statusDto.getPrice()));
    }

    @Operation(summary = "Delete menu item by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK")
    })
    @DeleteMapping("/{id}/delete")
    public void deleteMenuItemById(@PathVariable("id") Long id) {
        menuItemService.deleteMenuItem(id);
    }

    @Operation(summary = "Create new menu item for restaurant by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            schema = @Schema(implementation = RestaurantMenuItemDto.class))),
            @ApiResponse(responseCode = "404", description = "Restaurant was not found")
    })
    @PostMapping("/create")
    public ResponseEntity<RestaurantMenuItemDto> createMenuItem(@RequestBody SaveMenuItemDto menuItemDto)
            throws ResourceNotFoundException {
        return ResponseEntity.ok(menuItemService.saveMenuItem(menuItemDto));
    }

}
