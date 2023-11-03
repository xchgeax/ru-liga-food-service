package ru.liga.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.liga.dto.RestaurantCreationDto;
import ru.liga.dto.RestaurantDto;
import ru.liga.entity.Restaurant;
import ru.liga.entity.RestaurantStatus;
import ru.liga.exception.ResourceNotFoundException;
import ru.liga.mapper.RestaurantMapper;
import ru.liga.repo.RestaurantRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantMapper restaurantMapper;

    public Page<Restaurant> getRestaurantList(Integer pageIndex, Integer pageCount) {
        Pageable page = PageRequest.of(pageIndex, pageCount);

        return restaurantRepository.findAll(page);
    }

    public RestaurantDto getRestaurantById(Long id) throws ResourceNotFoundException {
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Restaurant does not exist"));

        return restaurantMapper.restaurantToRestaurantDto(restaurant);
    }

    public RestaurantDto createRestaurant(RestaurantCreationDto restaurantCreationDto) {
        Restaurant restaurant = restaurantMapper.restaurantCreationDtoToRestaurant(restaurantCreationDto);

        restaurantRepository.save(restaurant);
        return restaurantMapper.restaurantToRestaurantDto(restaurant);
    }

    public List<RestaurantDto> findRestaurantsByStatus(RestaurantStatus status) {
        List<Restaurant> restaurantList = restaurantRepository.findRestaurantsByStatus(status);

        return restaurantMapper.restaurantsToRestaurantDtos(restaurantList);
    }

    public void updateRestaurantStatus(Long id, RestaurantStatus status) throws ResourceNotFoundException {
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Restaurant does not exist"));

        restaurant.setStatus(status);
        restaurantRepository.save(restaurant);
    }
}
