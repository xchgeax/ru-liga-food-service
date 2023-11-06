package ru.liga.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.liga.entity.Customer;

import javax.transaction.Transactional;
import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Transactional
    @Query("select c from Customer c where c.phone = :phone")
    List<Customer> findCustomerByPhone(@Param("phone") String phone);
}
