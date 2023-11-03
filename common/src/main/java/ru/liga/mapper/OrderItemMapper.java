package ru.liga.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.liga.dto.OrderItemDto;
import ru.liga.entity.OrderItem;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

    @Mapping(source = "item.restaurantMenuItem.id", target = "menuItemId")
    OrderItemDto orderItemToOrderItemDto(OrderItem item);
}
