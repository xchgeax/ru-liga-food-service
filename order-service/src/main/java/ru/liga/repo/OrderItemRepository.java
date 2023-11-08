package ru.liga.repo;

import org.springframework.data.repository.CrudRepository;
import ru.liga.entity.OrderItem;

public interface OrderItemRepository extends CrudRepository<OrderItem, Long> {

}
