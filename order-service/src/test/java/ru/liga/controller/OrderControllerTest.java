package ru.liga.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.liga.controllers.OrderController;
import ru.liga.dto.*;
import ru.liga.entity.*;
import ru.liga.exception.IncorrectOrderStateException;
import ru.liga.exception.ResourceNotFoundException;
import ru.liga.service.OrderService;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = OrderController.class)
class OrderControllerTest {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private OrderService orderService;

    private OrderDto dto;
    private Order order;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        dto = OrderDto.builder()
                .id(UUID.randomUUID())
                .timestamp(Timestamp.valueOf(LocalDateTime.now()))
                .restaurant(new RestaurantDto())
                .items(List.of(new OrderItemDto()))
                .build();

        order = Order.builder()
                .courier(new Courier())
                .id(UUID.randomUUID())
                .items(List.of(new OrderItem()))
                .restaurant(new Restaurant())
                .status(OrderStatus.CUSTOMER_CREATED)
                .build();
    }

    @Test
    @WithMockUser
    void testCreateOrder_OkWhenValid() throws Exception {
        OrderConfirmationDto confirmationDto = new OrderConfirmationDto();
        confirmationDto.setId(dto.getId());
        OrderCreationDto newOrderDto = new OrderCreationDto(1L, 1L,
                List.of(new OrderItemCreationDto(1L, 1)));

        when(orderService.createOrder(any(OrderCreationDto.class))).thenReturn(confirmationDto);

        mvc.perform(post("/api/orders")
                        .content(mapper.writeValueAsString(newOrderDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(dto.getId().toString()), UUID.class));
    }

    @Test
    @WithMockUser
    void testUpdateOrder_OkWhenValid() throws Exception {
        doNothing().when(orderService).updateOrderStatus(any(UUID.class), any(OrderStatus.class));

        mvc.perform(put("/api/orders/" + order.getId().toString())
                        .content(mapper.writeValueAsString(OrderStatus.CUSTOMER_CREATED))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void testUpdateOrder_404WhenNotFound() throws Exception {
        doThrow(ResourceNotFoundException.class).when(orderService).updateOrderStatus(any(UUID.class), any(OrderStatus.class));

        mvc.perform(put("/api/orders/" + order.getId().toString())
                        .content(mapper.writeValueAsString(OrderStatus.CUSTOMER_CREATED))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void testUpdateOrder_400BadRequest() throws Exception {
        doThrow(IncorrectOrderStateException.class).when(orderService).updateOrderStatus(any(UUID.class), any(OrderStatus.class));

        mvc.perform(put("/api/orders/" + order.getId().toString())
                        .content(mapper.writeValueAsString(OrderStatus.CUSTOMER_CREATED))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }


}