package ru.liga.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

@Schema(description = "DTO for confirmation of a new menu item")
@Data
@Accessors(chain = true)
public class SaveMenuItemConfirmationDto {

    @Schema(description = "ID of the new item")
    private Long id;
}
