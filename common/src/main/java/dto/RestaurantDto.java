package dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

@Schema(description = "Restaurant DTO")
@Data
@Accessors(chain = true)
public class RestaurantDto {

    @Schema(description = "Restaurant name")
    private String name;

    @Schema(description = "Restaurant address")
    private String address;

    @Schema(description = "Restaurant distance")
    private Long distance;
}
