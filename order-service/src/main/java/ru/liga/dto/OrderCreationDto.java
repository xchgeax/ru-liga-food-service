package ru.liga.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Schema(description = "DTO for creation of a new order")
@Data
@Accessors(chain = true)
public class OrderCreationDto {

    @Schema(description = "ID of the restaurant the order was made at")
    @JsonProperty("restaurant_id")
    private Long restaurantId;

    @Schema(description = "Ordered items")
    @JsonProperty("menu_items")
    private List<OrderItemCreationDto> menuItems;
}
