package ru.liga.service;

import lombok.SneakyThrows;
import org.aspectj.weaver.ast.Or;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ru.liga.dto.OrderConfirmationDto;
import ru.liga.dto.OrderCreationDto;
import ru.liga.dto.OrderDto;
import ru.liga.entity.Customer;
import ru.liga.entity.Order;
import ru.liga.entity.OrderStatus;
import ru.liga.entity.Restaurant;
import ru.liga.exception.ResourceNotFoundException;
import ru.liga.mapper.OrderMapper;
import ru.liga.repo.CustomerRepository;
import ru.liga.repo.OrderRepository;
import ru.liga.repo.RestaurantRepository;
import ru.liga.service.impl.OrderServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceUnitTest {


    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderMapper orderMapper;

    @InjectMocks
    private OrderServiceImpl orderService;


    @Test
    public void testGetOrderList() {
        Pageable pageable = PageRequest.of(0, 5);
        Page<Order> mockPage = new PageImpl<>(List.of(new Order(), new Order()));

        when(orderRepository.findAll(pageable)).thenReturn(mockPage);

        Page<OrderDto> result = orderService.getOrderList(0, 5);

        assertEquals(mockPage.getTotalElements(), result.getTotalElements());
        assertEquals(2, result.getContent().size());
    }

    @Test
    public void testGetOrderByStatus() {
        OrderStatus status = OrderStatus.CUSTOMER_CREATED;
        List<Order> orderList = Arrays.asList(new Order(), new Order());
        List<OrderDto> orderDtos = Arrays.asList(OrderDto.builder().build(), OrderDto.builder().build());

        when(orderRepository.findOrderByStatus(status)).thenReturn(orderList);
        when(orderMapper.orderToOrderDto(orderList)).thenReturn(orderDtos);

        List<OrderDto> result = orderService.getOrderListByStatus(status);

        assertEquals(orderList.size(), result.size());
    }

    @SneakyThrows
    @Test
    public void testGetOrderById() {
        Long orderId = 1L;
        Order order = new Order();
        OrderDto orderDto = OrderDto.builder().build();

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(orderMapper.orderToOrderDto(order)).thenReturn(orderDto);

        OrderDto result = orderService.getOrderById(orderId);

        assertEquals(orderDto, result);
    }

    @Test
    public void testGetOrderById_WhenNotFound() {
        Long orderId = 1L;

        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> orderService.getOrderById(orderId));
    }

}
