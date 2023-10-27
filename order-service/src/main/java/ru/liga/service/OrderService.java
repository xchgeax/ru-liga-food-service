package ru.liga.service;

import ru.liga.clients.RestaurantsFeign;
import ru.liga.dto.OrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.dto.*;
import ru.liga.entity.*;
import ru.liga.exception.NoOrderItemsSuppliedException;
import ru.liga.exception.ResourceNotFoundException;
import ru.liga.mapper.OrderMapper;
import ru.liga.mapper.RestaurantMenuItemMapper;
import ru.liga.repo.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final RestaurantRepository restaurantRepository;
    private final CustomerRepository customerRepository;
    private final RestaurantMenuItemRepository restaurantMenuItemRepository;
    private final RabbitMQOrderService rabbitMQProducerService;
    private final OrderMapper orderMapper;
    private final RestaurantMenuItemMapper restaurantMenuItemMapper;
    private final RestaurantsFeign restaurantsFeign;


    public OrderDto getOrderById(Long id) throws ResourceNotFoundException {
        Order order = orderRepository.findOrderById(id);

        if (order == null) throw new ResourceNotFoundException("Order not found");

        return orderMapper.orderToOrderDto(order);
    }

    public List<OrderDto> getOrderListByStatus(OrderStatus status) {
        List<Order> orderList = orderRepository.findOrderByStatus(status);

        return orderMapper.orderToOrderDto(orderList);
    }

    public List<OrderDto> getOrderList() {
        List<Order> orders = orderRepository.getOrders();

        return orderMapper.orderToOrderDto(orders);
    }

    public OrderConfirmationDto createOrder(Long restaurantId, List<OrderItemDto> orderItemDtoList) throws ResourceNotFoundException, NoOrderItemsSuppliedException {
        Restaurant restaurant = restaurantRepository.findRestaurantById(restaurantId);

        if (restaurant == null) throw new ResourceNotFoundException("Restaurant does not exist");

        Map<RestaurantMenuItem, Integer> orderedMenuItems = validateAndGetOrderedItems(restaurantId, orderItemDtoList);

        // TODO: get real customer with security
        Customer customer = customerRepository.findCustomerById(1L);

        Order order = new Order();
        order.setStatus(OrderStatus.CUSTOMER_CREATED);
        order.setRestaurant(restaurant);
        order.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));
        order.setCustomer(customer);
        orderRepository.save(order);

        orderedMenuItems.forEach((orderedMenuItem, quantity) -> saveNewOrderItem(order, orderedMenuItem, quantity));

        OrderConfirmationDto orderConfirmationDto = new OrderConfirmationDto();
        orderConfirmationDto.setArrivalTime(1L);
        orderConfirmationDto.setPaymentUrl("http://localhost/payment");
        orderConfirmationDto.setId(order.getId());

        return orderConfirmationDto;
    }

    private Map<RestaurantMenuItem, Integer> validateAndGetOrderedItems(Long restaurantId, List<OrderItemDto> orderItemDtoList) throws NoOrderItemsSuppliedException {

        List<RestaurantMenuItemDto> restaurantMenuItemsDtoList = restaurantsFeign.getMenuItemsByRestaurantId(restaurantId);
        Map<RestaurantMenuItem, Integer> orderedItems = new HashMap<>();

        orderItemDtoList.forEach(orderItemDto -> restaurantMenuItemsDtoList.stream()
                .filter(index -> index.getId().equals(orderItemDto.getMenuItemId()))
                .findFirst()
                .ifPresent(restaurantMenuItemDto ->
                        orderedItems.put(restaurantMenuItemMapper.restaurantMenuItemDtoToItem(restaurantMenuItemDto),
                                orderItemDto.getQuantity())));

        if (orderedItems.isEmpty()) throw new NoOrderItemsSuppliedException();

        return orderedItems;
    }

    public void updateOrderStatus(Long id, OrderStatus status) throws ResourceNotFoundException {
        Order order = orderRepository.findOrderById(id);

        if (order == null) throw new ResourceNotFoundException("Order not found");

        if (status == OrderStatus.DELIVERY_PENDING)
            rabbitMQProducerService.sendOrderToDeliveryService(orderMapper.orderToOrderDto(order));

        order.setStatus(status);
        orderRepository.save(order);
    }

    public OrderItemConfirmationDto saveNewOrderItem(Long orderId, Long menuItemId, Integer quantity) throws ResourceNotFoundException {
        Order order = orderRepository.findOrderById(orderId);
        RestaurantMenuItem restaurantMenuItem = restaurantMenuItemRepository.findRestaurantMenuItemById(menuItemId);

        if (restaurantMenuItem == null) throw new ResourceNotFoundException("Menu item not found");
        if (order == null) throw new ResourceNotFoundException("Order not found");

        return saveNewOrderItem(order, restaurantMenuItem, quantity);
    }

    public OrderItemConfirmationDto saveNewOrderItem(Order order, RestaurantMenuItem restaurantMenuItem, Integer quantity) {
        OrderItemConfirmationDto orderItemConfirmationDto = new OrderItemConfirmationDto();

        OrderItem orderItem = OrderItem.builder()
                .order(order)
                .restaurantMenuItem(restaurantMenuItem)
                .quantity(quantity)
                .price(restaurantMenuItem.getPrice() * quantity)
                .build();
        orderItemRepository.save(orderItem);

        return orderItemConfirmationDto.setId(orderItem.getId());
    }


    public void deleteOrderItem(Long id) {
        orderItemRepository.deleteById(id);
    }
}
