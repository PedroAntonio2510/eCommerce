package io.github.api.domain.enums;

import io.github.api.domain.Order;
import io.github.api.domain.enums.strategy.PaymentMethods;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.function.BiConsumer;

public enum PaymentType {

    PIX((order, rabbitTemplate) -> PaymentMethods.pix(order, rabbitTemplate)),
    CREDIT_CARD((order, rabbitTemplate) -> PaymentMethods.creditCard(order, rabbitTemplate));

    private final BiConsumer<Order, RabbitTemplate> paymentStrategy;

    PaymentType(BiConsumer<Order, RabbitTemplate> paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    public void processPayment(Order order, RabbitTemplate rabbitTemplate) {
        paymentStrategy.accept(order, rabbitTemplate);
    }
}
