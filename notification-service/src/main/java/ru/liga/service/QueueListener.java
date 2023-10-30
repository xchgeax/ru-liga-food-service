package ru.liga.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import ru.liga.entity.Order;
import ru.liga.exception.ResourceNotFoundException;
import ru.liga.repo.OrderRepository;

@EnableRabbit
@Service
@Slf4j
@RequiredArgsConstructor
public class QueueListener {
    private final OrderRepository orderRepository;

    @SneakyThrows
    @RabbitListener(queues = "courierNotification")
    public void handleCourierNotification(String message) {
        ObjectMapper objectMapper = new ObjectMapper();

        Long orderId = objectMapper.readValue(message, Long.class);
        Order order = orderRepository.findById(orderId).orElseThrow(ResourceNotFoundException::new);

        log.info("Order id={} assigned to courier id={}", order.getId(), order.getCourier().getId());
    }
}
