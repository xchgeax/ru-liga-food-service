package ru.liga.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.dto.RestaurantMenuItemDto;
import ru.liga.dto.SaveMenuItemDto;
import ru.liga.dto.UpdatePriceConfirmationDto;
import ru.liga.entity.Restaurant;
import ru.liga.entity.RestaurantMenuItem;
import ru.liga.exception.ResourceNotFoundException;
import ru.liga.mapper.RestaurantMenuItemMapper;
import ru.liga.repo.RestaurantMenuItemRepository;
import ru.liga.repo.RestaurantRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuItemService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantMenuItemRepository menuItemRepository;
    private final RestaurantMenuItemMapper restaurantMenuItemMapper;

    public UpdatePriceConfirmationDto updatePrice(Long id, int price) {
        menuItemRepository.updatePrice(id, price);
        return new UpdatePriceConfirmationDto().setPrice(price).setId(id);
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
