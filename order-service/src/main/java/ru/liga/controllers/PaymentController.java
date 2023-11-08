package ru.liga.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.liga.exception.IncorrectOrderStateException;
import ru.liga.exception.ResourceNotFoundException;
import ru.liga.service.PaymentService;

import java.util.UUID;

@Tag(name = "API для оплаты")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payment")
public class PaymentController {

    private final PaymentService paymentService;

    @Operation(summary = "Оплатить заказ")
    @PostMapping("/{id}/pay")
    public void payById(@PathVariable("id") UUID id) throws ResourceNotFoundException, IncorrectOrderStateException {
        paymentService.pay(id);
    }
}
