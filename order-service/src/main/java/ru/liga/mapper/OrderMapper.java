package ru.liga.mapper;

import org.mapstruct.Mapper;
import ru.liga.dto.OrderDto;
import ru.liga.entity.Order;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderDto orderToOrderDto(Order order);

}
