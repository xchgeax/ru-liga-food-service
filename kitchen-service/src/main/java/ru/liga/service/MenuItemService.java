package ru.liga.service;

import ru.liga.dto.RestaurantMenuItemDto;
import ru.liga.dto.SaveMenuItemDto;
import ru.liga.exception.ResourceNotFoundException;

import java.util.List;

public interface MenuItemService {

    RestaurantMenuItemDto updatePrice(Long id, Long price) throws ResourceNotFoundException;

    List<RestaurantMenuItemDto> findMenuItemsByRestaurantId(Long restaurantId);

    RestaurantMenuItemDto saveMenuItem(SaveMenuItemDto menuItemDto) throws ResourceNotFoundException;

    void deleteMenuItem(Long id);
}
