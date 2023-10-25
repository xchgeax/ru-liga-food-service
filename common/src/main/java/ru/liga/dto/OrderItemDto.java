package ru.liga.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

@Schema(description = "DTO for ordered item from a restaurant menu")
@Data
@Accessors(chain = true)
public class OrderItemDto {

    @Schema(description = "Restaurant menu item")
    @JsonProperty("menu_item_id")
    private Long menuItemId;

    @Schema(description = "Total price of an order")
    private Long price;

    @Schema(description = "Item quantity")
    private Integer quantity;
}
