package ru.liga.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import ru.liga.dto.OrderDto;
import ru.liga.entity.Courier;
import ru.liga.entity.CourierStatus;
import ru.liga.entity.Order;
import ru.liga.entity.OrderStatus;
import ru.liga.exception.ResourceNotFoundException;
import ru.liga.mapper.OrderMapper;
import ru.liga.repo.CourierRepository;
import ru.liga.repo.OrderRepository;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class QueueListener {

    private final CourierRepository courierRepository;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @SneakyThrows
    @RabbitListener(queues = "delivery")
    public void processDeliveryQueue(String message) {
        ObjectMapper objectMapper = new ObjectMapper();

        Long orderId = objectMapper.readValue(message, Long.class);
        Order order = orderRepository.findById(orderId).orElseThrow(ResourceNotFoundException::new);

        List<Courier> courierList = courierRepository.findCourierByStatus(CourierStatus.AVAILABLE);

        if (!courierList.isEmpty()) {
            Courier courier = courierList.get(0);
            order.setCourier(courier);
            courier.setStatus(CourierStatus.BUSY);
            order.setStatus(OrderStatus.DELIVERY_PICKING);

            courierRepository.save(courier);
            orderRepository.save(order);
        }
    }
}
