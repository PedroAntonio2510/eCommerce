package io.github.notification.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {

    @Value("${rabbitmq.exchange.notification}")
    private String notificationEx;

    @Value("${rabbitmq.queue.created}")
    private String orderCreatedQueue;

    @Value("${rabbitmq.queue.pending}")
    private String orderPendingQueue;

    @Value("${rabbitmq.queue.complete}")
    private String orderCompleteQueue;

    @Value("${rabbitmq.queue.email-verification}")
    private String emailVerificationQueue;

    @Bean
    public DirectExchange notificationExchange() {
        return ExchangeBuilder
                .directExchange("notification.ex")
                .durable(true)
                .build();
    }

    @Bean
    public Queue emailVerificationQueue() {
        return new Queue("email-verification.ms-notification", true);
    }

    @Bean
    public Queue orderCreatedQueue() {
        return new Queue("order-created.ms-notification", true);
    }

    @Bean
    public Queue orderPendingQueue() {
        return new Queue("order-pending.ms-notification", true);
    }

    @Bean
    public Queue orderCompleteQueue() {
        return new Queue("order-complete.ms-notification", true);
    }

    @Bean
    public Binding bindingEmailVerificationQueue(DirectExchange notificationExchange,
                                                 Queue emailVerificationQueue) {
        return BindingBuilder.bind(emailVerificationQueue)
                .to(notificationExchange).with("email.verification");
    }

    @Bean
    public Binding bindingOrderCreatedQueue(DirectExchange notificationExchange,
                                            Queue orderCreatedQueue) {
        return BindingBuilder.bind(orderCreatedQueue)
                .to(notificationExchange).with("order-created");
    }

    @Bean
    public Binding bindingOrderUpdatedQueue(DirectExchange notifcationExchange,
                                            Queue orderPendingQueue) {
        return BindingBuilder.bind(orderPendingQueue)
                .to(notifcationExchange).with("order-pending");
    }

    @Bean
    public Binding bindingOrderCompleteQueue(DirectExchange notifcationExchange,
                                            Queue orderCompleteQueue) {
        return BindingBuilder.bind(orderCompleteQueue)
                .to(notifcationExchange).with("order-complete");
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

}
