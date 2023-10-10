package ru.liga.notificationservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

@Schema(description = "DTO for delivery")
@Data
@Accessors(chain = true)
public class DeliveryDto {

    @Schema(description = "ID of the order being delivered")
    @JsonProperty("order_id")
    private Long orderId;

    @Schema(description = "Restaurant order was made at")
    private RestaurantDto restaurant;

    @Schema(description = "Customer order was made by")
    private CustomerDto customer;

    // ???
    @Schema(description = "Payment information")
    private Long payment;
}
