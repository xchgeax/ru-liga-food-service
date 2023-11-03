package ru.liga.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;


@Schema(description = "DTO for saving a new menu item")
@Data
@Accessors(chain = true)
public class SaveMenuItemDto {

    @Schema(description = "Restaurant id new item belongs to")
    private Long restaurantId;

    @Schema(description = "Name of a new menu item")
    private String name;

    @Schema(description = "Description of a new menu item")
    private String description;

    @Schema(description = "Image of a new menu item")
    private String image;

    @Schema(description = "Price of a new menu item")
    private Long price;
}
