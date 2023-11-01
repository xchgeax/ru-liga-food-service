package ru.liga.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.liga.dto.RestaurantCreationDto;
import ru.liga.dto.RestaurantDto;
import ru.liga.entity.Restaurant;
import ru.liga.entity.RestaurantStatus;
import ru.liga.exception.ResourceNotFoundException;
import ru.liga.service.RestaurantService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Tag(name = "Restaurant management API")
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/restaurant")
public class RestaurantController {

    private final RestaurantService restaurantService;

    @Operation(summary = "Get all restaurants")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            schema = @Schema(implementation = Restaurant.class)))
    })
    @GetMapping
    public ResponseEntity<Page<Restaurant>> getRestaurantList(@PositiveOrZero @RequestParam Integer pageIndex,
                                                              @Positive @RequestParam Integer pageCount) {
        log.info("Received GET request to get a list of all restaurants");
        return ResponseEntity.ok(restaurantService.getRestaurantList(pageIndex, pageCount));
    }

    @Operation(summary = "Get restaurant by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            schema = @Schema(implementation = RestaurantDto.class))),
            @ApiResponse(responseCode = "404", description = "Restaurant was not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<RestaurantDto> getRestaurantById(@PathVariable("id") Long id)
            throws ResourceNotFoundException {
        log.info("Received GET request to get a restaurant by id {}", id);
        return ResponseEntity.ok(restaurantService.getRestaurantById(id));
    }

    @Operation(summary = "Create new restaurant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            schema = @Schema(implementation = RestaurantDto.class)))
    })
    @PostMapping
    public ResponseEntity<RestaurantDto> createRestaurant(RestaurantCreationDto restaurantCreationDto) {
        log.info("Received POST request to create new restaurant {}", restaurantCreationDto);
        return ResponseEntity.ok(restaurantService.createRestaurant(restaurantCreationDto));
    }

    @Operation(summary = "Update restaurant status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Restaurant was not found")
    })
    @PostMapping("/{id}/status")
    public void updateRestaurantStatus(@PathVariable("id") Long id, @RequestParam RestaurantStatus restaurantStatus)
            throws ResourceNotFoundException {
        log.info("Received POST request to update restaurant status by id {}, status {}", id, restaurantStatus);
        restaurantService.updateRestaurantStatus(id, restaurantStatus);
    }

    @Operation(summary = "Find restaurants by status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            schema = @Schema(implementation = RestaurantDto.class)))
    })
    @GetMapping("/status/{status}")
    public ResponseEntity<List<RestaurantDto>> findRestaurantsByStatus(@PathVariable("status") RestaurantStatus status) {
        log.info("Received GET request to get all restaurants by status {}", status);
        return ResponseEntity.ok(restaurantService.findRestaurantsByStatus(status));
    }

}
