package ru.liga.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.liga.entity.Customer;
import ru.liga.exception.ResourceNotFoundException;
import ru.liga.repo.CustomerRepository;
import ru.liga.service.CustomerService;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public Page<Customer> getCustomerList(Integer pageIndex, Integer pageCount) {
        Pageable page = PageRequest.of(pageIndex, pageCount);

        return customerRepository.findAll(page);
    }

    public Customer getCustomerById(Long id) throws ResourceNotFoundException {
        return customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer does not exist"));
    }
}
