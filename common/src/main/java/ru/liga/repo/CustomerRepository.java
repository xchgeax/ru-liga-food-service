package ru.liga.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.liga.entity.Customer;

import javax.transaction.Transactional;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    @Transactional
    @Query("select c from Customer c where c.id = :id")
    Customer findCustomerById(@Param("id") Long id);

    @Transactional
    @Query("select c from Customer c where c.phone = :phone")
    Customer findCustomerByPhone(@Param("phone") String phone);
}
