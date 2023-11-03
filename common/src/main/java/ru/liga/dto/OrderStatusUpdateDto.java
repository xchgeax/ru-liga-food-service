package ru.liga.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import ru.liga.entity.OrderStatus;

@Schema(description = "DTO for updating order status")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class OrderStatusUpdateDto {

    @Schema(description = "New order status")
    private OrderStatus status;
}
