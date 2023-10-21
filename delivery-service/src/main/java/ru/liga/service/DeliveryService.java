package ru.liga.service;

import dto.OrderDto;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.stereotype.Service;
import ru.liga.clients.OrdersFeign;
import ru.liga.dto.DeliveryDto;
import ru.liga.dto.DeliveryStatusConfirmationDto;
import ru.liga.entity.OrderStatus;

import java.net.ConnectException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeliveryService {

    public DeliveryService(OrdersFeign ordersFeign) {
        this.ordersFeign = ordersFeign;
    }

    private final OrdersFeign ordersFeign;

    public List<DeliveryDto> getDeliveryListByStatus(OrderStatus status) {
        List<OrderDto> orders = ordersFeign.getOrderListByStatus(status);

        return orders.stream().map(orderDto -> new DeliveryDto()
                .setOrderId(orderDto.getId())
                .setRestaurant(orderDto.getRestaurant())
                .setCustomer(orderDto.getCustomer())
                .setPayment(-1L))
                .collect(Collectors.toList());
    }

    public DeliveryStatusConfirmationDto updateDeliveryStatus(Long id, String status) {
        DeliveryStatusConfirmationDto statusConfirmationDto = new DeliveryStatusConfirmationDto();
        statusConfirmationDto.setId(id);
        statusConfirmationDto.setStatus(status);
        return statusConfirmationDto;
    }
}
