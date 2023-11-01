package ru.liga.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

@Schema(description = "DTO for updating item price")
@Data
@Accessors(chain = true)
public class UpdatePriceDto {

    @Schema(description = "New price")
    private Long price;
}
