package ru.liga.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
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
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class QueueListener {

    private final CourierRepository courierRepository;
    private final OrderRepository orderRepository;
    private final RabbitMQNotificationServiceImpl rabbitMQNotificationService;

    @RabbitListener(queues = "delivery")
    public void processDeliveryQueue(String message) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        UUID orderId = objectMapper.readValue(message, UUID.class);

        log.info("Received new delivery to process id={}", orderId);

        Order order;
        try {
            order = orderRepository.findById(orderId).orElseThrow(ResourceNotFoundException::new);
        } catch (ResourceNotFoundException e) {
            log.warn("Order id={} doesn't exists", orderId);
            return;
        }

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
        } else {
            order.setStatus(OrderStatus.DELIVERY_PENDING);
        }

        orderRepository.save(order);
        rabbitMQNotificationService.sendNotificationOnOrder(orderId);
    }
}
