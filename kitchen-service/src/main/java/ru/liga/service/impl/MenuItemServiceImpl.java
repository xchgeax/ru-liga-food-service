package ru.liga.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.dto.RestaurantMenuItemDto;
import ru.liga.dto.SaveMenuItemDto;
import ru.liga.entity.Restaurant;
import ru.liga.entity.RestaurantMenuItem;
import ru.liga.exception.ResourceNotFoundException;
import ru.liga.mapper.RestaurantMenuItemMapper;
import ru.liga.repo.RestaurantMenuItemRepository;
import ru.liga.repo.RestaurantRepository;
import ru.liga.service.MenuItemService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuItemServiceImpl implements MenuItemService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantMenuItemRepository menuItemRepository;
    private final RestaurantMenuItemMapper restaurantMenuItemMapper;

    public RestaurantMenuItemDto updatePrice(Long id, Long price) throws ResourceNotFoundException {
        RestaurantMenuItem restaurantMenuItem = menuItemRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Menu item does not exist"));

        restaurantMenuItem.setPrice(price);
        menuItemRepository.save(restaurantMenuItem);

        return restaurantMenuItemMapper.restaurantMenuItemToDto(restaurantMenuItem);
    }

    public List<RestaurantMenuItemDto> findMenuItemsByRestaurantId(Long restaurantId) {
        List<RestaurantMenuItem> restaurantMenuItems = menuItemRepository.findRestaurantMenuItemsByRestaurantId(restaurantId);

        return restaurantMenuItemMapper.restaurantMenuItemToDto(restaurantMenuItems);
    }

    public RestaurantMenuItemDto saveMenuItem(SaveMenuItemDto menuItemDto) throws ResourceNotFoundException {
        Restaurant restaurant = restaurantRepository.findById(menuItemDto.getRestaurantId()).orElseThrow(
                () -> new ResourceNotFoundException("Restaurant does not exist"));

        RestaurantMenuItem restaurantMenuItem = restaurantMenuItemMapper.saveMenuItemDtoToRestaurantMenuItem(restaurant,
                menuItemDto);

        restaurantMenuItem = menuItemRepository.save(restaurantMenuItem);

        return restaurantMenuItemMapper.restaurantMenuItemToDto(restaurantMenuItem);
    }

    public void deleteMenuItem(Long id) {
        menuItemRepository.deleteById(id);
    }
}
