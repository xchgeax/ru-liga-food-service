package ru.liga.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import ru.liga.entity.CourierStatus;

@Schema(description = "Confirmation for an updated status of specified courier")
@Data
@Accessors(chain = true)
public class CourierStatusConfirmationDto {

    @Schema(description = "Courier id")
    private Long id;

    @Schema(description = "Courier status")
    private CourierStatus status;
}
