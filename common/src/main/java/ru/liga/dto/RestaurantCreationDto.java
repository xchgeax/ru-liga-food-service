package ru.liga.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "DTO for creation of a new restaurant")
@Data
public class RestaurantCreationDto {

    @Schema(description = "Restaurant address")
    private String address;

    @Schema(description = "Restaurant name")
    private String name;
}
