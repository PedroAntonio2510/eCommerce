package io.github.api.service;

import io.github.api.domain.Order;
import lombok.AllArgsConstructor;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RabbitMqNotificationService {

    private RabbitTemplate rabbitTemplate;

    public void notify(Order order, String exchange, String routingKey, MessagePostProcessor messagePostProcessor) {
        rabbitTemplate.convertAndSend(exchange, routingKey, order, messagePostProcessor);
    }

    public void notify(Order order, String exchange, String routingKey) {
        rabbitTemplate.convertAndSend(exchange, routingKey, order);
    }

    public void orderCreatedNotification(Order order, String exchange){
        rabbitTemplate.convertAndSend(exchange, "order-created", order);
    }

    public void orderCreatedNotification(Order order, String exchange, MessagePostProcessor messagePostProcessor){
        rabbitTemplate.convertAndSend(exchange, "order-created", order, messagePostProcessor);
    }

    public void orderUpdateNotification(Order order, String exchange) {
        rabbitTemplate.convertAndSend(exchange, "order-update", order);
    }

    public void orderCompleteNotification(Order order, String exchange) {
        rabbitTemplate.convertAndSend(exchange, "order-complete", order);
    }
}
