package ru.liga.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import ru.liga.entity.*;
import ru.liga.exception.ResourceNotFoundException;
import ru.liga.repo.CourierRepository;
import ru.liga.repo.OrderRepository;
import ru.liga.util.Coordinates;
import ru.liga.util.CoordinatesCalculator;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class QueueListener {

    private final CourierRepository courierRepository;
    private final OrderRepository orderRepository;
    private final RabbitMQNotificationServiceImpl rabbitMQNotificationService;

    @SneakyThrows
    @RabbitListener(queues = "delivery")
    public void processDeliveryQueue(String message) {
        ObjectMapper objectMapper = new ObjectMapper();

        Long orderId = objectMapper.readValue(message, Long.class);
        Order order = orderRepository.findById(orderId).orElseThrow(ResourceNotFoundException::new);

        List<Courier> courierList = courierRepository.findCourierByStatus(CourierStatus.AVAILABLE);

        if (!courierList.isEmpty()) {
            Customer customer = order.getCustomer();
            Coordinates customerCoordinates = new Coordinates(customer.getCoordinates());

            Courier closestCourier = courierList.stream()
                    .sorted(Comparator.comparingDouble(courier -> CoordinatesCalculator.distance(
                            new Coordinates(courier.getCoordinates()), customerCoordinates)))
                    .collect(Collectors.toList()).get(0);

            closestCourier.setStatus(CourierStatus.BUSY);
            order.setCourier(closestCourier);
            order.setStatus(OrderStatus.DELIVERY_PICKING);

            orderRepository.save(order);
            rabbitMQNotificationService.sendCourierNotificationOnOrder(orderId);
        }
    }
}
