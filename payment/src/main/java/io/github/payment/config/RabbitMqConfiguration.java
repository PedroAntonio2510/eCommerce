package io.github.payment.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfiguration {

    @Value("${rabbitmq.exchange.payment}")
    private String paymentEx;

    @Bean
    public DirectExchange paymentExchange() {
        return ExchangeBuilder
                .directExchange(paymentEx)
                .durable(true)
                .build();
    }

    @Bean
    public Queue paymentCreditQueue() {
        return new Queue("payment-credit.ms-payment", true);
    }

    @Bean
    public Queue paymentPixQueue() {
        return new Queue("payment-pix.ms-payment", true);
    }

    @Bean
    public Queue paymentPendingQueue() {
        return new Queue("payment-pending.ms-payment", true);
    }

    @Bean
    public Queue paymentPaidQueue() {
        return new Queue("payment-paid.ms-payment", true);
    }

    @Bean
    public Binding bindPaymentCreatedCreditQueue(DirectExchange paymentExchange,
                                           Queue paymentCreditQueue) {
        return BindingBuilder.bind(paymentCreditQueue)
                .to(paymentExchange).with("order-created-credit");
    }

    @Bean
    public Binding bindPaymentCreatedPixQueue(DirectExchange paymentExchange,
                                           Queue paymentPixQueue) {
        return BindingBuilder.bind(paymentPixQueue)
                .to(paymentExchange).with("order-created-pix");
    }

    @Bean
    public Binding bindPaymentPendingQueue(DirectExchange paymentExchange,
                                           Queue paymentPendingQueue) {
        return BindingBuilder.bind(paymentPendingQueue)
                .to(paymentExchange).with("order-pending");
    }

    @Bean
    public Binding bindPaymentPaidQueue(DirectExchange paymentExchange,
                                        Queue paymentPaidQueue) {
        return BindingBuilder.bind(paymentPaidQueue)
                .to(paymentExchange).with("payment-paid");
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}

