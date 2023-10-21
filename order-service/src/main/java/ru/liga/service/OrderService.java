package ru.liga.service;

import ru.liga.dto.CustomerDto;
import ru.liga.dto.ItemDto;
import ru.liga.dto.OrderDto;
import ru.liga.dto.RestaurantDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.liga.dto.*;
import ru.liga.entity.*;
import ru.liga.exception.ResourceNotFoundException;
import ru.liga.repo.OrderItemRepository;
import ru.liga.repo.OrderRepository;
import ru.liga.repo.RestaurantMenuItemRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    OrderRepository orderRepository;
    OrderItemRepository orderItemRepository;
    RestaurantMenuItemRepository restaurantMenuItemRepository;

    RabbitMQOrderService rabbitMQProducerService;

    @Autowired
    public OrderService(OrderRepository orderRepository,
                        OrderItemRepository orderItemRepository,
                        RestaurantMenuItemRepository restaurantMenuItemRepository,
                        RabbitMQOrderService rabbitMQOrderService) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.restaurantMenuItemRepository = restaurantMenuItemRepository;
        this.rabbitMQProducerService = rabbitMQOrderService;
    }

    public OrderDto getOrderById(Long id) throws ResourceNotFoundException {
        Order order = orderRepository.findOrderById(id);

        if (order == null) throw new ResourceNotFoundException("Order not found");

        return mapOrderToDto(order);
    }

    public List<OrderDto> getOrderListByStatus(OrderStatus status) {
        List<Order> orderList = orderRepository.findOrderByStatus(status);
        return orderList.stream().map(this::mapOrderToDto).collect(Collectors.toList());
    }

    // TODO: временно, нужны мапперы
    private OrderDto mapOrderToDto(Order order) {
        RestaurantDto restaurantDto = new RestaurantDto()
                .setName(order.getRestaurant().getName())
                .setAddress(order.getRestaurant().getAddress());
        CustomerDto customerDto = new CustomerDto()
                .setAddress(order.getCustomer().getAddress())
                .setPhone(order.getCustomer().getPhone());
        List<ItemDto> itemList = order.getOrderItemList().stream()
                .map(this::mapOrderItemToDto).collect(Collectors.toList());

        return new OrderDto()
                .setId(order.getId())
                .setRestaurant(restaurantDto)
                .setCustomer(customerDto)
                .setItems(itemList)
                .setTimestamp(order.getTimestamp());
    }

    private ItemDto mapOrderItemToDto(OrderItem item) {
        RestaurantMenuItem restaurantMenuItem = item.getRestaurantMenuItem();

        return new ItemDto()
                .setDescription(restaurantMenuItem.getDescription())
                .setImage(restaurantMenuItem.getImage())
                .setPrice(item.getPrice())
                .setQuantity(item.getQuantity());
    }

    public List<OrderDto> getOrderList() {
        return orderRepository.getOrders().stream().map(this::mapOrderToDto).collect(Collectors.toList());
    }

    public OrderConfirmationDto createOrder(Long restaurantId, List<OrderItemDto> menuItems) {
        return new OrderConfirmationDto().setId(0L).setArrivalTime(123L).setPaymentUrl("google.com");
    }

    public void updateOrderStatus(Long id, OrderStatus status) throws ResourceNotFoundException {
        Order order = orderRepository.findOrderById(id);

        if (order == null) throw new ResourceNotFoundException("Order not found");

        if (status == OrderStatus.DELIVERY_PENDING)
            rabbitMQProducerService.sendOrderToDeliveryService(mapOrderToDto(order));

        order.setStatus(status);
        orderRepository.save(order);
    }

    public OrderItemConfirmationDto saveNewOrderItem(Long orderId, Long menuItemId, Integer quantity) throws ResourceNotFoundException {

        OrderItemConfirmationDto orderItemConfirmationDto = new OrderItemConfirmationDto();
        RestaurantMenuItem restaurantMenuItem = restaurantMenuItemRepository.findRestaurantMenuItemById(menuItemId);
        Order order = orderRepository.findOrderById(orderId);

        if (restaurantMenuItem == null) throw new ResourceNotFoundException("Menu item not found");
        if (order == null) throw new ResourceNotFoundException("Order not found");

        OrderItem orderItem = OrderItem.builder()
                .order(order)
                .restaurantMenuItem(restaurantMenuItem)
                .quantity(quantity)
                .price(restaurantMenuItem.getPrice() * quantity)
                .build();
        orderItemRepository.save(orderItem);

        return orderItemConfirmationDto.setId(orderItem.getId());
    }

    public void deleteOrderItem(Long id) throws ResourceNotFoundException {
        OrderItem orderItem = orderItemRepository.findOrderItemById(id);

        if (orderItem == null) throw new ResourceNotFoundException("Order item not found");

        orderItemRepository.delete(orderItem);
    }
}
