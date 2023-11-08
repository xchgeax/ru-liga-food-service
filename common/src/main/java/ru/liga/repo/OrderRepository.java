package ru.liga.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.liga.entity.Order;
import ru.liga.entity.OrderStatus;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {

    @Transactional
    @Query("select ord from Order ord where ord.status = :status")
    List<Order> findOrderByStatus(@Param("status") OrderStatus status);

}
