package ru.liga.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.liga.dto.DeliveryDto;
import ru.liga.dto.OrderDto;
import ru.liga.entity.Order;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DeliveryMapper {

    @Mapping(source = "order.id", target = "orderId")
    DeliveryDto orderToDeliveryDto(Order order);

    List<DeliveryDto> orderToDeliveryDto(List<Order> order);

}
