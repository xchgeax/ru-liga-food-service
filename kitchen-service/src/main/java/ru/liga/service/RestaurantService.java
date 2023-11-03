package ru.liga.service;

import org.springframework.data.domain.Page;
import ru.liga.dto.RestaurantCreationDto;
import ru.liga.dto.RestaurantDto;
import ru.liga.entity.Restaurant;
import ru.liga.entity.RestaurantStatus;
import ru.liga.exception.ResourceNotFoundException;

import java.util.List;

public interface RestaurantService {

    Page<Restaurant> getRestaurantList(Integer pageIndex, Integer pageCount);

    RestaurantDto getRestaurantById(Long id) throws ResourceNotFoundException;

    RestaurantDto createRestaurant(RestaurantCreationDto restaurantCreationDto);

    List<RestaurantDto> findRestaurantsByStatus(RestaurantStatus status);

    void updateRestaurantStatus(Long id, RestaurantStatus status) throws ResourceNotFoundException;
}
