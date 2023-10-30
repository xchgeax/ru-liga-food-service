package ru.liga.mapper;

import org.mapstruct.Mapper;
import ru.liga.dto.CustomerDto;
import ru.liga.entity.Customer;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerDto customerToCustomerDto(Customer customer);
}
