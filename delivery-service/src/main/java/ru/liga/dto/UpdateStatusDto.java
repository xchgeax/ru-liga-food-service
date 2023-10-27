package ru.liga.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import ru.liga.entity.OrderStatus;

@Schema(description = "DTO for updating delivery status")
@Data
@Accessors(chain = true)
public class UpdateStatusDto {

    @Schema(description = "New delivery order status")
    @JsonProperty("order_action")
    private OrderStatus status;
}
