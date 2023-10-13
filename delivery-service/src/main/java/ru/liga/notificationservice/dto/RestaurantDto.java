package ru.liga.notificationservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

@Schema(description = "DTO for restaurant")
@Data
@Accessors(chain = true)
public class RestaurantDto {

    @Schema(description = "Restaurant address")
    private String address;

    @Schema(description = "Restaurant distance")
    private Long distance;
}
