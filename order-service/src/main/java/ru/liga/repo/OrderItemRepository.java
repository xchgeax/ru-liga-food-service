package ru.liga.repo;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.liga.entity.Order;

import javax.transaction.Transactional;
import java.util.List;

public interface OrderItemRepository extends CrudRepository<Order, Long> {

    @Transactional
    @Query("select orderitem from OrderItem orderitem where orderitem.id = :id")
    Order findOrderItemById(@Param("id") Long id);

    @Transactional
    @Query("select orderitem from OrderItem orderitem where orderitem.order = :id")
    List<Order> findOrderItemByOrderId(@Param("id") Long id);
}
