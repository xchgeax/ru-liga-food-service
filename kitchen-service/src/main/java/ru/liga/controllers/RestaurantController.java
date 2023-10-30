package ru.liga.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.liga.dto.RestaurantDto;
import ru.liga.entity.Restaurant;
import ru.liga.entity.RestaurantStatus;
import ru.liga.service.RestaurantService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Tag(name = "Restaurant management API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurant")
public class RestaurantController {

    private final RestaurantService restaurantService;

    @Operation(summary = "Get all restaurants")
    @GetMapping
    public ResponseEntity<Page<Restaurant>> getRestaurantList(@PositiveOrZero @RequestParam Integer pageIndex,
                                                              @Positive @RequestParam Integer pageCount) {
        return ResponseEntity.ok(restaurantService.getRestaurantList(pageIndex, pageCount));
    }

    @Operation(summary = "Find restaurants by status")
    @GetMapping("/status/{status}")
    public ResponseEntity<List<RestaurantDto>> findRestaurantsByStatus(@PathVariable("status") RestaurantStatus status) {
        return ResponseEntity.ok(restaurantService.findRestaurantsByStatus(status));
    }

}
