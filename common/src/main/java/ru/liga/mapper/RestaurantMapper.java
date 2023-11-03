package ru.liga.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.liga.dto.RestaurantCreationDto;
import ru.liga.dto.RestaurantDto;
import ru.liga.entity.Restaurant;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", constant = "CLOSED")
    Restaurant restaurantCreationDtoToRestaurant(RestaurantCreationDto creationDto);

    RestaurantDto restaurantToRestaurantDto(Restaurant restaurant);

    List<RestaurantDto> restaurantsToRestaurantDtos(List<Restaurant> restaurantList);
}
