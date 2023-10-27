package ru.liga.repo;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.liga.entity.Order;
import ru.liga.entity.OrderStatus;

import javax.transaction.Transactional;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Modifying
    @Transactional
    @Query("update Order ord set ord.status = :status where ord.id = :id")
    void updateOrderStatus(@Param("id") Long id, @Param("status") OrderStatus status);

    @Transactional
    @Query("select ord from Order ord where ord.status = :status")
    List<Order> findOrderByStatus(@Param("status") OrderStatus status);

}
