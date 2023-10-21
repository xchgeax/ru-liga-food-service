package ru.liga.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

@Schema(description = "Item DTO")
@Data
@Accessors(chain = true)
public class ItemDto {

    @Schema(description = "Item price")
    private Long price;

    @Schema(description = "Item quantity")
    private Integer quantity;

    @Schema(description = "Item description")
    private String description;

    @Schema(description = "Item image url")
    private String image;
}
