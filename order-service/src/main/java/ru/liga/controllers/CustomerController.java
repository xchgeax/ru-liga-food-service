package ru.liga.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.liga.entity.Customer;
import ru.liga.exception.ResourceNotFoundException;
import ru.liga.service.impl.CustomerServiceImpl;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Tag(name = "Customer management API")
@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerServiceImpl customerService;

    @Operation(summary = "Get all customers")
    @GetMapping
    public ResponseEntity<Page<Customer>> getCustomerListById(@PositiveOrZero @RequestParam Integer pageIndex,
                                                              @Positive @RequestParam Integer pageCount) {
        return ResponseEntity.ok(customerService.getCustomerList(pageIndex, pageCount));
    }

    @Operation(summary = "Get customer by id")
    @GetMapping("/id/{customerId}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long customerId) throws ResourceNotFoundException {
        return ResponseEntity.ok(customerService.getCustomerById(customerId));
    }
}
