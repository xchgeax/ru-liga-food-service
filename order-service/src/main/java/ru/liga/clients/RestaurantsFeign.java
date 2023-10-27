package ru.liga.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.liga.dto.RestaurantMenuItemDto;

import java.util.List;

@FeignClient(name = "kitchen-service", url = "http://localhost:8081/restaurant")
public interface RestaurantsFeign {

    @GetMapping("/{id}/items")
    List<RestaurantMenuItemDto> getMenuItemsByRestaurantId(@PathVariable Long id);
}
