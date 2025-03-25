package io.github.api.config;

import io.github.api.domain.Order;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;
import java.util.function.BiConsumer;

@Configuration
public class BiConsumerConfig {

    @Bean
    public BiConsumer<Order, RabbitTemplate> orderBiConsumer() {
        return (order, rabbitTemplate) -> {
            Optional.ofNullable(order.getPayment())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid payment type!"))
                    .processPayment(order, rabbitTemplate);
        };
    }
}
