package ru.liga.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

@Schema(description = "Restaurant menu item DTO")
@Data
@Accessors(chain = true)
public class RestaurantMenuItemDto {

    @Schema(description = "Restaurant menu item id")
    private Long id;

    @Schema(description = "Restaurant menu item name")
    private String name;

    @Schema(description = "Restaurant menu item price")
    private Long price;

    @Schema(description = "Restaurant menu item description")
    private String description;

    @Schema(description = "Restaurant menu item image")
    private String image;
}
