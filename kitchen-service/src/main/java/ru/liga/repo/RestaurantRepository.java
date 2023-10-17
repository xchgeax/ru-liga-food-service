package ru.liga.repo;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.liga.entity.Restaurant;
import ru.liga.entity.RestaurantStatus;

import javax.transaction.Transactional;

public interface RestaurantRepository extends CrudRepository<Restaurant, Long>  {

    @Transactional
    @Query("select r from Restaurant r where r.id = :id")
    Restaurant findRestaurantById(@Param("id") Long id);

    @Transactional
    @Query("select r from Restaurant r where r.status = :status")
    Restaurant findRestaurantByStatus(@Param("status") RestaurantStatus status);
}
