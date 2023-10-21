package ru.liga.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.dto.SaveMenuItemConfirmationDto;
import ru.liga.dto.UpdatePriceConfirmationDto;
import ru.liga.entity.Restaurant;
import ru.liga.entity.RestaurantMenuItem;
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

    public SaveMenuItemConfirmationDto saveMenuItem(String name, int price, String description, String image, Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findRestaurantById(restaurantId);

        if (restaurant == null) return new SaveMenuItemConfirmationDto().setId(-1L);

        RestaurantMenuItem restaurantMenuItem = new RestaurantMenuItem();
        restaurantMenuItem.setRestaurant(restaurant);
        restaurantMenuItem.setName(name);
        restaurantMenuItem.setPrice(price);
        restaurantMenuItem.setDescription(description);
        restaurantMenuItem.setImage(image);

        restaurantMenuItem = menuItemRepository.save(restaurantMenuItem);

        return new SaveMenuItemConfirmationDto().setId(restaurantMenuItem.getId());
    }

    public void deleteMenuItem(Long id) {
        RestaurantMenuItem menuItem = menuItemRepository.findRestaurantMenuItemById(id);

        if (menuItem == null) return;

        menuItemRepository.delete(menuItem);
    }
}
