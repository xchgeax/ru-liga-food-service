package ru.liga.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.entity.OrderStatus;
import ru.liga.exception.IncorrectOrderStateException;
import ru.liga.exception.ResourceNotFoundException;
import ru.liga.service.OrderService;
import ru.liga.service.PaymentService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final OrderService orderService;

    @Override
    public void pay(UUID orderId) throws ResourceNotFoundException, IncorrectOrderStateException {
        orderService.updateOrderStatus(orderId, OrderStatus.CUSTOMER_PAID);
    }
}
