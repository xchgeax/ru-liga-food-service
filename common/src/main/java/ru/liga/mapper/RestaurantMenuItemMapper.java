package ru.liga.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.liga.dto.RestaurantMenuItemDto;
import ru.liga.dto.SaveMenuItemDto;
import ru.liga.entity.Restaurant;
import ru.liga.entity.RestaurantMenuItem;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RestaurantMenuItemMapper {

    @Mapping(target = "name", source = "saveMenuItemDto.name")
    @Mapping(target = "restaurant", source = "restaurant")
    @Mapping(target = "id", ignore = true)
    RestaurantMenuItem saveMenuItemDtoToRestaurantMenuItem(Restaurant restaurant, SaveMenuItemDto saveMenuItemDto);

    RestaurantMenuItemDto restaurantMenuItemToDto(RestaurantMenuItem restaurantMenuItem);

    List<RestaurantMenuItemDto> restaurantMenuItemToDto(List<RestaurantMenuItem> restaurantMenuItem);

}
