package ru.liga.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import ru.liga.entity.CourierStatus;

@Schema(description = "DTO for updating courier status")
@Data
@Accessors(chain = true)
public class CourierUpdateStatusDto {

    @Schema(description = "New courier status")
    private CourierStatus status;
}
