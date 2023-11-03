package ru.liga.config;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoutingMQConfig {

    @Bean
    public Declarables newOrderToCourierNotificationQueue() {
        Queue newOrderToKitchen = new Queue("courierNotification", false);
        DirectExchange directExchange = new DirectExchange("directExchange");

        return new Declarables(newOrderToKitchen, directExchange,
                BindingBuilder.bind(newOrderToKitchen).to(directExchange).with("courierNotification"));
    }

}
