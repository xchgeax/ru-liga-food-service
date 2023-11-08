package ru.liga.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

@Schema(description = "DTO for response on order creation")
@Data
@Accessors(chain = true)
public class OrderConfirmationDto {

    @Schema(description = "Order ID")
    private UUID id;

    @Schema(description = "Secret payment URL")
    @JsonProperty("secret_payment_url")
    private String paymentUrl;

    // arrival time = seconds required to cook and deliver the order or calculated timestamp of actual arrival?
    @Schema(description = "Estimated time of arrival")
    @JsonProperty("estimated_time_of_arrival")
    private Long arrivalTime;
}
