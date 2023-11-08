package ru.liga.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.liga.dto.OrderConfirmationDto;
import ru.liga.dto.OrderCreationDto;
import ru.liga.dto.OrderDto;
import ru.liga.dto.OrderItemCreationDto;
import ru.liga.entity.*;
import ru.liga.exception.IncorrectOrderStateException;
import ru.liga.exception.NoOrderItemsSuppliedException;
import ru.liga.exception.ResourceNotFoundException;
import ru.liga.mapper.OrderMapper;
import ru.liga.repo.*;
import ru.liga.service.OrderService;
import ru.liga.service.RabbitMQNotificationService;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final RestaurantRepository restaurantRepository;
    private final OrderItemRepository orderItemRepository;
    private final RestaurantMenuItemRepository menuItemRepository;
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final RabbitMQNotificationService rabbitMQNotificationService;

    private final OrderMapper orderMapper;


    public OrderDto getOrderById(UUID id) throws ResourceNotFoundException {
        Order order = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        return orderMapper.orderToOrderDto(order);
    }

    public Page<OrderDto> getOrderList(Integer pageIndex, Integer pageCount) {
        Pageable page = PageRequest.of(pageIndex, pageCount);
        Page<Order> orders = orderRepository.findAll(page);

        return orders.map(orderMapper::orderToOrderDto);
    }

    public OrderConfirmationDto createOrder(OrderCreationDto orderCreationDto) throws ResourceNotFoundException, NoOrderItemsSuppliedException {
        Restaurant restaurant = restaurantRepository.findById(orderCreationDto.getRestaurantId())
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant does not exist"));

        Customer customer = customerRepository.findById(orderCreationDto.getClientId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer does not exist"));

        Order order = new Order();

        List<OrderItem> orderedMenuItems = validateAndGetOrderedItems(order, orderCreationDto.getRestaurantId(),
                orderCreationDto.getMenuItems());

        order.setStatus(OrderStatus.CUSTOMER_CREATED);
        order.setRestaurant(restaurant);
        order.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));
        order.setCustomer(customer);

        orderRepository.save(order);
        orderItemRepository.saveAll(orderedMenuItems);

        rabbitMQNotificationService.sendNotificationOnOrder(order.getId());

        OrderConfirmationDto orderConfirmationDto = new OrderConfirmationDto();
        orderConfirmationDto.setArrivalTime(1L);
        orderConfirmationDto.setPaymentUrl("http://localhost/payment");
        orderConfirmationDto.setId(order.getId());

        return orderConfirmationDto;
    }

    @Override
    public void cancelOrder(UUID id) throws ResourceNotFoundException, IncorrectOrderStateException {
        updateOrderStatus(id, OrderStatus.CUSTOMER_CANCELLED);
    }

    public List<OrderItem> validateAndGetOrderedItems(Order order, Long restaurantId, List<OrderItemCreationDto> orderItemDtoList) throws NoOrderItemsSuppliedException {

        Map<Long, Integer> orderedMenuItemQuantity = orderItemDtoList.stream()
                .collect(Collectors.toMap(OrderItemCreationDto::getMenuItemId, OrderItemCreationDto::getQuantity));

        Set<Long> orderedMenuItemIds = orderedMenuItemQuantity.keySet();
        List<RestaurantMenuItem> menuItems = menuItemRepository.findAllByRestaurantIdAndIdIn(restaurantId, orderedMenuItemIds);

        if (menuItems.isEmpty()) throw new NoOrderItemsSuppliedException();

        List<OrderItem> orderedItems = new ArrayList<>();

        menuItems.forEach(menuItem -> {
            Integer quantity = orderedMenuItemQuantity.get(menuItem.getId());

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setRestaurantMenuItem(menuItem);
            orderItem.setQuantity(quantity);
            orderItem.setPrice(menuItem.getPrice() * quantity);

            orderedItems.add(orderItem);
        });

        return orderedItems;
    }

    public void updateOrderStatus(UUID orderId, OrderStatus orderStatus) throws ResourceNotFoundException, IncorrectOrderStateException {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        if (!orderStatus.isAcceptableForOrder(order)) {
            throw new IncorrectOrderStateException();
        }

        order.setStatus(orderStatus);

        orderRepository.save(order);
        rabbitMQNotificationService.sendNotificationOnOrder(orderId);
    }
}
