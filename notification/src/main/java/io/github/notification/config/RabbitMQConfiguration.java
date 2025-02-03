package io.github.notification.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {

    @Value("${rabbitmq.queue.created}")
    private String orderCreatedQueue;

    @Value("${rabbitmq.queue.update}")
    private String orderUpdatedQueue;

    @Value("${rabbitmq.queue.complete}")
    private String orderCompleteQueue;

    @Value("${rabbitmq.queue.email-verification}")
    private String emailVerificationQueue;

    @Bean
    public DirectExchange notificationExchange() {
        return ExchangeBuilder.directExchange("notification.ex").durable(true).build();
    }

    @Bean
    public Queue emailVerificationQueue() {
        return new Queue(emailVerificationQueue, true);
    }

    @Bean
    public Queue orderCreatedQueue() {
        return new Queue(orderCreatedQueue, true);
    }

    @Bean
    public Queue orderUpdatedQueue() {
        return new Queue(orderUpdatedQueue, true);
    }

    @Bean
    public Queue orderCompleteQueue() {
        return new Queue(orderCompleteQueue, true);
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
                                            Queue orderUpdatedQueue) {
        return BindingBuilder.bind(orderUpdatedQueue)
                .to(notifcationExchange).with("order-update");
    }

    @Bean
    public Binding bindingOrderCompleteQueue(DirectExchange notifcationExchange,
                                            Queue orderUpdatedQueue) {
        return BindingBuilder.bind(orderUpdatedQueue)
                .to(notifcationExchange).with("order-complete");
    }



    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}
