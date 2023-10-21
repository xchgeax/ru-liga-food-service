package ru.liga.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

@Schema(description = "DTO for confirmation of an added order item")
@Data
@Accessors(chain = true)
public class OrderItemConfirmationDto {

    @Schema(description = "Created order item id")
    private Long id;
}
