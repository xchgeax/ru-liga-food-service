package ru.liga.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.liga.dto.CustomerDto;
import ru.liga.dto.OrderDto;
import ru.liga.dto.OrderItemDto;
import ru.liga.entity.Customer;
import ru.liga.entity.Order;
import ru.liga.entity.OrderItem;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderDto orderToOrderDto(Order order);

    List<OrderDto> ordersToOrderDtos(List<Order> order);

    CustomerDto customerToCustomerDto(Customer customer);

    @Mapping(source = "item.restaurantMenuItem.id", target = "menuItemId")
    OrderItemDto orderItemToOrderItemDto(OrderItem item);
}
