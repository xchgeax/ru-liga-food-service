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
import ru.liga.controllers.DeliveryController;
import ru.liga.entity.*;
import ru.liga.service.DeliveryService;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = DeliveryController.class)
public class DeliveryControllerTest {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private DeliveryService deliveryService;

    private Order order;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

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
    void testTakeDelivery_OkWhenValid() throws Exception {
        doNothing().when(deliveryService).takeDelivery(order.getId());

        mvc.perform(post("/api/delivery/" + order.getId().toString() + "/take")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void testCompleteDelivery_OkWhenValid() throws Exception {
        doNothing().when(deliveryService).takeDelivery(order.getId());

        mvc.perform(post("/api/delivery/" + order.getId().toString() + "/complete")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
