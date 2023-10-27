package ru.liga.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import ru.liga.entity.OrderStatus;

@Schema(description = "Confirmation for an updated status of specified delivery")
@Data
@Accessors(chain = true)
public class DeliveryStatusConfirmationDto {

    @Schema(description = "Delivery id")
    private Long id;

    @Schema(description = "Delivery status")
    private OrderStatus status;
}
