package dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;

@Schema(description = "DTO for customer")
@Data
@Accessors(chain = true)
public class CustomerDto {

    @Schema(description = "Customer address")
    private String address;

    @Schema(description = "Customer phone number")
    private String phone;

    @Schema(description = "Customer distance")
    private Long distance;
}
