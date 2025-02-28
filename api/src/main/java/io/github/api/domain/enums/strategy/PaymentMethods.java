package io.github.api.domain.enums.strategy;

import io.github.api.domain.Order;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class PaymentMethods {

    public static void creditCard(Order order, RabbitTemplate rabbitTemplate) {
        rabbitTemplate.convertAndSend("payment.ex", "order-created-credit", order);
    }

    public static void pix(Order order, RabbitTemplate rabbitTemplate) {
        rabbitTemplate.convertAndSend("payment.ex", "order-created-pix", order);
    }
}
