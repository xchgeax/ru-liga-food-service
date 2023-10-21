package ru.liga.repo;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.liga.entity.RestaurantMenuItem;

import javax.transaction.Transactional;

public interface RestaurantMenuItemRepository extends CrudRepository<RestaurantMenuItem, Long> {

    @Transactional
    @Query("select item from RestaurantMenuItem item where item.id = :id")
    RestaurantMenuItem findRestaurantMenuItemById(@Param("id") Long id);

    @Transactional
    @Query("select item from RestaurantMenuItem item where item.restaurant = :id")
    RestaurantMenuItem findRestaurantMenuItemByRestaurantId(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query("update RestaurantMenuItem item set item.price = :price where item.id = :id")
    void updatePrice(@Param(value = "id") long id, @Param(value = "price") int price);
}
