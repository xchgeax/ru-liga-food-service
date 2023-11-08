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
import ru.liga.controllers.KitchenController;
import ru.liga.entity.*;
import ru.liga.service.KitchenService;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = KitchenController.class)
public class KitchenControllerTest {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private KitchenService kitchenService;

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
    void testAcceptOrder_OkWhenValid() throws Exception {
        doNothing().when(kitchenService).acceptOrder(order.getId());

        mvc.perform(post("/api/kitchen/" + order.getId().toString() + "/accept")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void testDenyOrder_OkWhenValid() throws Exception {
        doNothing().when(kitchenService).denyOrder(order.getId());

        mvc.perform(post("/api/kitchen/" + order.getId().toString() + "/deny")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void testReadyOrder_OkWhenValid() throws Exception {
        doNothing().when(kitchenService).readyOrder(order.getId());

        mvc.perform(post("/api/kitchen/" + order.getId().toString() + "/ready")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
