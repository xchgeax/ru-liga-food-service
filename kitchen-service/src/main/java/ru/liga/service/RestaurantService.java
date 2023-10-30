package ru.liga.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.dto.*;
import ru.liga.entity.Restaurant;
import ru.liga.entity.RestaurantMenuItem;
import ru.liga.entity.RestaurantStatus;
import ru.liga.exception.ResourceNotFoundException;
import ru.liga.mapper.RestaurantMapper;
import ru.liga.mapper.RestaurantMenuItemMapper;
import ru.liga.repo.RestaurantMenuItemRepository;
import ru.liga.repo.RestaurantRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantMenuItemRepository menuItemRepository;
    private final RestaurantRepository restaurantRepository;
    private final RestaurantMapper restaurantMapper;
    private final RestaurantMenuItemMapper restaurantMenuItemMapper;

    public List<RestaurantDto> findRestaurantsByStatus(RestaurantStatus status) {
        List<Restaurant> restaurantList = restaurantRepository.findRestaurantsByStatus(status);

        return restaurantMapper.restaurantsToRestaurantDtos(restaurantList);
    }

    public UpdatePriceConfirmationDto updatePrice(Long id, int price) {
        menuItemRepository.updatePrice(id, price);
        return new UpdatePriceConfirmationDto().setPrice(price).setId(id);
    }

    public List<RestaurantMenuItemDto> findMenuItemsByRestaurantId(Long restaurantId) {
        List<RestaurantMenuItem> restaurantMenuItems = menuItemRepository.findRestaurantMenuItemsByRestaurantId(restaurantId);

       return restaurantMenuItemMapper.restaurantMenuItemToDto(restaurantMenuItems);
    }

    public SaveMenuItemConfirmationDto saveMenuItem(SaveMenuItemDto menuItemDto) throws ResourceNotFoundException {
        Restaurant restaurant = restaurantRepository.findRestaurantById(menuItemDto.getRestaurantId());

        if (restaurant == null) throw new ResourceNotFoundException("Restaurant not found");

        RestaurantMenuItem restaurantMenuItem = new RestaurantMenuItem();
        restaurantMenuItem.setRestaurant(restaurant);
        restaurantMenuItem.setName(menuItemDto.getName());
        restaurantMenuItem.setPrice(menuItemDto.getPrice());
        restaurantMenuItem.setDescription(menuItemDto.getDescription());
        restaurantMenuItem.setImage(menuItemDto.getImage());

        restaurantMenuItem = menuItemRepository.save(restaurantMenuItem);

        return new SaveMenuItemConfirmationDto().setId(restaurantMenuItem.getId());
    }

    public void deleteMenuItem(Long id) {
        menuItemRepository.deleteById(id);
    }
}
