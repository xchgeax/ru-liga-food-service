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
@RequestMapping("/restaurant")
public class RestaurantController {

    private final RestaurantService restaurantService;

    @Operation(summary = "Find restaurants by status")
    @GetMapping("/status/{status}")
    public ResponseEntity<List<RestaurantDto>> findRestaurantsByStatus(@PathVariable("status") RestaurantStatus status) {
        return ResponseEntity.ok(restaurantService.findRestaurantsByStatus(status));
    }

}
