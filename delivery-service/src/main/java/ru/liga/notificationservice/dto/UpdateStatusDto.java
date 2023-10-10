package ru.liga.notificationservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

@Schema(description = "DTO for updating delivery status")
@Data
@Accessors(chain = true)
public class UpdateStatusDto {

    @Schema(description = "New delivery order status")
    @JsonProperty("order_action")
    private String status;
}
