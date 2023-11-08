package ru.liga.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import ru.liga.entity.Order;
import ru.liga.repo.OrderRepository;
import ru.liga.service.NotificationService;

import java.util.Optional;
import java.util.UUID;

@EnableRabbit
@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationQueueListener {
    private final OrderRepository orderRepository;
    private final NotificationService notificationService;

    @RabbitListener(queues = "notification")
    public void handleNotification(String message) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        UUID orderId = objectMapper.readValue(message, UUID.class);

        Optional<Order> optionalOrder = orderRepository.findById(orderId);

        optionalOrder.ifPresent(order -> {
            String customerNotificationMessage = order.getStatus().getCustomerNotificationMessage(order);
            String courierNotificationMessage = order.getStatus().getCourierNotificationMessage(order);

            if (!customerNotificationMessage.isEmpty()) {
                notificationService.sendCustomerNotification(order.getCustomer().getId(), customerNotificationMessage);
            }
            if (!courierNotificationMessage.isEmpty() && order.getCourier() != null) {
                notificationService.sendCourierNotification(order.getCourier().getId(), courierNotificationMessage);
            }
        });
    }
}
