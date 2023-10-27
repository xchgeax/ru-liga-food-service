package ru.liga.mapper;

import org.mapstruct.Mapper;
import ru.liga.dto.RestaurantMenuItemDto;
import ru.liga.entity.RestaurantMenuItem;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RestaurantMenuItemMapper {

    RestaurantMenuItem restaurantMenuItemDtoToItem(RestaurantMenuItemDto restaurantMenuItem);

    RestaurantMenuItemDto restaurantMenuItemToDto(RestaurantMenuItem restaurantMenuItem);

    List<RestaurantMenuItemDto> restaurantMenuItemToDto(List<RestaurantMenuItem> restaurantMenuItem);

    List<RestaurantMenuItem> restaurantMenuItemDtoToItem(List<RestaurantMenuItemDto> restaurantMenuItem);
}
