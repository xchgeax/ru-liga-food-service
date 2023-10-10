package ru.liga.notificationservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

@Schema(description = "DTO for customer")
@Data
@Accessors(chain = true)
public class CustomerDto {

    @Schema(description = "Customer address")
    private String address;

    @Schema(description = "Customer distance")
    private Long distance;
}
