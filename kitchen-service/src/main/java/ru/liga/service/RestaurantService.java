package ru.liga.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.dto.SaveMenuItemConfirmationDto;
import ru.liga.dto.SaveMenuItemDto;
import ru.liga.dto.UpdatePriceConfirmationDto;
import ru.liga.entity.Restaurant;
import ru.liga.entity.RestaurantMenuItem;
import ru.liga.exception.ResourceNotFoundException;
import ru.liga.repo.RestaurantMenuItemRepository;
import ru.liga.repo.RestaurantRepository;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantMenuItemRepository menuItemRepository;
    private final RestaurantRepository restaurantRepository;

    public UpdatePriceConfirmationDto updatePrice(Long id, int price) {
        menuItemRepository.updatePrice(id, price);
        return new UpdatePriceConfirmationDto().setPrice(price).setId(id);
    }

    public SaveMenuItemConfirmationDto saveMenuItem(Long restaurantId, SaveMenuItemDto menuItemDto) throws ResourceNotFoundException {
        Restaurant restaurant = restaurantRepository.findRestaurantById(restaurantId);

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
        RestaurantMenuItem menuItem = menuItemRepository.findRestaurantMenuItemById(id);

        if (menuItem == null) return;

        menuItemRepository.delete(menuItem);
    }
}
