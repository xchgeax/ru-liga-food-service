package ru.liga.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import ru.liga.entity.OrderStatus;

import java.util.Date;
import java.util.List;

@Schema(description = "Order DTO")
@Data
@Builder
@Accessors(chain = true)
public class OrderDto {

    @Schema(description = "Order ID")
    private Long id;

    @Schema(description = "Restaurant order was made at")
    private RestaurantDto restaurant;

    @Schema(description = "Customer order was made by")
    private CustomerDto customer;

    @Schema(description = "Status of an order")
    private OrderStatus status;

    @Schema(description = "Time order was made at")
    private Date timestamp;

    @Schema(description = "Ordered items")
    private List<OrderItemDto> items;
}