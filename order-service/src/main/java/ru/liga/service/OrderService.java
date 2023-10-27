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

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderItemService orderItemService;
    private final RestaurantRepository restaurantRepository;
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
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

    public OrderConfirmationDto createOrder(Long restaurantId, List<OrderItemCreationDto> orderItemDtoList) throws ResourceNotFoundException, NoOrderItemsSuppliedException {
        Restaurant restaurant = restaurantRepository.findRestaurantById(restaurantId);

        if (restaurant == null) throw new ResourceNotFoundException("Restaurant does not exist");

        // TODO: get real customer with security
        Customer customer = customerRepository.findCustomerById(1L);

        Order order = new Order();

        List<OrderItem> orderedMenuItems = validateAndGetOrderedItems(order, restaurantId, orderItemDtoList);

        order.setStatus(OrderStatus.CUSTOMER_CREATED);
        order.setRestaurant(restaurant);
        order.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));
        order.setCustomer(customer);

        orderRepository.save(order);
        orderItemService.saveOrderItemList(orderedMenuItems);

        OrderConfirmationDto orderConfirmationDto = new OrderConfirmationDto();
        orderConfirmationDto.setArrivalTime(1L);
        orderConfirmationDto.setPaymentUrl("http://localhost/payment");
        orderConfirmationDto.setId(order.getId());

        return orderConfirmationDto;
    }

    private List<OrderItem> validateAndGetOrderedItems(Order order, Long restaurantId, List<OrderItemCreationDto> orderItemDtoList) throws NoOrderItemsSuppliedException {

        List<RestaurantMenuItemDto> restaurantMenuItemsDtoList = restaurantsFeign.getMenuItemsByRestaurantId(restaurantId);
        List<OrderItem> orderedItems = new ArrayList<>(Collections.emptyList());

        orderItemDtoList.forEach(orderItemDto -> restaurantMenuItemsDtoList.stream()
                .filter(index -> index.getId().equals(orderItemDto.getMenuItemId()))
                .findFirst()
                .ifPresent(restaurantMenuItemDto ->
                {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setOrder(order);
                    orderItem.setRestaurantMenuItem(restaurantMenuItemMapper.restaurantMenuItemDtoToItem(restaurantMenuItemDto));
                    orderItem.setQuantity(orderItemDto.getQuantity());
                    orderItem.setPrice(restaurantMenuItemDto.getPrice() * orderItemDto.getQuantity());

                    orderedItems.add(orderItem);
                }));

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
}
