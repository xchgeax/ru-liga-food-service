package ru.liga.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

@Schema(description = "DTO for confirmation of an updated price")
@Data
@Accessors(chain = true)
public class UpdatePriceConfirmationDto {

    @Schema(description = "ID of the item")
    private Long id;

    @Schema(description = "New price")
    private int price;
}
