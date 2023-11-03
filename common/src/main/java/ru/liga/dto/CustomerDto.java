package ru.liga.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;

@Schema(description = "DTO for customer")
@Data
@Accessors(chain = true)
public class CustomerDto {

    @Schema(description = "Customer coordinates")
    private String coordinates;

    @Schema(description = "Customer phone number")
    private String phone;

}
