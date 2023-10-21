package ru.liga.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;


@Schema(description = "DTO for orders of specific restaurant")
@Data
@Accessors(chain = true)
public class OrderDto {

    @Schema(description = "ID of the order")
    private Long id;

    @Schema(description = "Ordered items")
    @JsonProperty("menu_items")
    private List<ItemDto> menuItems;
}
