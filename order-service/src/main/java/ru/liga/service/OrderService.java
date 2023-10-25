package ru.liga.service;

import ru.liga.dto.OrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.dto.*;
import ru.liga.entity.*;
import ru.liga.exception.ResourceNotFoundException;
import ru.liga.mapper.OrderMapper;
import ru.liga.repo.OrderItemRepository;
import ru.liga.repo.OrderRepository;
import ru.liga.repo.RestaurantMenuItemRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final RestaurantMenuItemRepository restaurantMenuItemRepository;
    private final RabbitMQOrderService rabbitMQProducerService;
    private final OrderMapper orderMapper;


    public OrderDto getOrderById(Long id) throws ResourceNotFoundException {
        Order order = orderRepository.findOrderById(id);

        if (order == null) throw new ResourceNotFoundException("Order not found");

        return orderMapper.orderToOrderDto(order);
    }

    public List<OrderDto> getOrderListByStatus(OrderStatus status) {
        List<Order> orderList = orderRepository.findOrderByStatus(status);

        return orderMapper.ordersToOrderDtos(orderList);
    }

    public List<OrderDto> getOrderList() {
        List<Order> orders = orderRepository.getOrders();

        return orderMapper.ordersToOrderDtos(orders);
    }

    public OrderConfirmationDto createOrder(Long restaurantId, List<OrderItemDto> menuItems) {
        return new OrderConfirmationDto().setId(0L).setArrivalTime(123L).setPaymentUrl("google.com");
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

    public void deleteOrderItem(Long id) {
        orderItemRepository.deleteById(id);
    }
}
