package ru.liga.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.liga.entity.Restaurant;
import ru.liga.entity.RestaurantStatus;

import javax.transaction.Transactional;
import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    @Transactional
    @Query("select r from Restaurant r where r.status = :status")
    List<Restaurant> findRestaurantsByStatus(@Param("status") RestaurantStatus status);
}
