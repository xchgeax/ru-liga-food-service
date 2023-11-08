package ru.liga.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@Schema(description = "DTO for creation of an ordered item from a restaurant menu")
@Data
@AllArgsConstructor
@Accessors(chain = true)
public class OrderItemCreationDto {

    @Schema(description = "Restaurant menu item")
    @JsonProperty("menu_item_id")
    private Long menuItemId;

    @Schema(description = "Item quantity")
    private Integer quantity;
}
