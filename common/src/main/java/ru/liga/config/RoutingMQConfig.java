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
    public Declarables deliveryQueue() {
        Queue deliveryQueue = new Queue("delivery", false);
        DirectExchange directExchange = new DirectExchange("directExchange");

        return new Declarables(deliveryQueue, directExchange,
                BindingBuilder.bind(deliveryQueue).to(directExchange).with("delivery"));
    }

    @Bean
    public Declarables notificationQueue() {
        Queue newOrderToKitchen = new Queue("notification", false);
        DirectExchange directExchange = new DirectExchange("directExchange");

        return new Declarables(newOrderToKitchen, directExchange,
                BindingBuilder.bind(newOrderToKitchen).to(directExchange).with("notification"));
    }

    @Bean
    public Declarables orderStatusQueue() {
        Queue orderStatusQueue = new Queue("orderStatus", false);
        DirectExchange directExchange = new DirectExchange("directExchange");

        return new Declarables(orderStatusQueue, directExchange,
                BindingBuilder.bind(orderStatusQueue).to(directExchange).with("orderStatus"));
    }
}
