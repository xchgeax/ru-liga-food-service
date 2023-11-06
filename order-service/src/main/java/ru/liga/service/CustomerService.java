package ru.liga.service;

import org.springframework.data.domain.Page;
import ru.liga.entity.Customer;
import ru.liga.exception.ResourceNotFoundException;

public interface CustomerService {

    Page<Customer> getCustomerList(Integer pageIndex, Integer pageCount);

    Customer getCustomerById(Long id) throws ResourceNotFoundException;
}
