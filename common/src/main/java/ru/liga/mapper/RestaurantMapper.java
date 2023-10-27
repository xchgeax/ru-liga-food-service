package ru.liga.mapper;


import org.mapstruct.Mapper;
import ru.liga.dto.RestaurantDto;
import ru.liga.entity.Restaurant;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {

    RestaurantDto restaurantToRestaurantDto(Restaurant restaurant);

    List<RestaurantDto> restaurantsToRestaurantDtos(List<Restaurant> restaurantList);
}
