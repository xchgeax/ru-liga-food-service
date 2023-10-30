package ru.liga.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.liga.dto.OrderConfirmationDto;
import ru.liga.dto.OrderDto;
import ru.liga.dto.OrderItemCreationDto;
import ru.liga.entity.*;
import ru.liga.exception.NoOrderItemsSuppliedException;
import ru.liga.exception.ResourceNotFoundException;
import ru.liga.mapper.OrderMapper;
import ru.liga.repo.CustomerRepository;
import ru.liga.repo.OrderRepository;
import ru.liga.repo.RestaurantRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderItemService orderItemService;
    private final RestaurantRepository restaurantRepository;
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;

    private final RabbitMQOrderService rabbitMQProducerService;
    private final OrderMapper orderMapper;


    public OrderDto getOrderById(Long id) throws ResourceNotFoundException {
        Order order = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        return orderMapper.orderToOrderDto(order);
    }

    public List<OrderDto> getOrderListByStatus(OrderStatus status) {
        List<Order> orderList = orderRepository.findOrderByStatus(status);

        return orderMapper.orderToOrderDto(orderList);
    }

    public Page<OrderDto> getOrderList(Integer pageIndex, Integer pageCount) {
        Pageable page = PageRequest.of(pageIndex, pageCount);
        Page<Order> orders = orderRepository.findAll(page);

        return orders.map(orderMapper::orderToOrderDto);
    }

    public OrderConfirmationDto createOrder(Long restaurantId, List<OrderItemCreationDto> orderItemDtoList) throws ResourceNotFoundException, NoOrderItemsSuppliedException {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(() -> new ResourceNotFoundException("Restaurant does not exist"));

        // TODO: get real customer with security
        Customer customer = customerRepository.findCustomerById(1L);

        Order order = new Order();

        List<OrderItem> orderedMenuItems = orderItemService.validateAndGetOrderedItems(order, restaurantId, orderItemDtoList);

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


    public void updateOrderStatus(Long id, OrderStatus status) throws ResourceNotFoundException {
        Order order = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        if (status == OrderStatus.DELIVERY_PENDING)
            rabbitMQProducerService.sendOrderToDeliveryService(id);

        order.setStatus(status);
        orderRepository.save(order);
    }
}
