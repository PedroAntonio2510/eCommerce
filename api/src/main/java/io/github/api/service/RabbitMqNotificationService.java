package io.github.api.service;

import io.github.api.domain.Order;
import io.github.api.domain.User;
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

    public void notify(User user, String exchange, String routingKey) {
        rabbitTemplate.convertAndSend(exchange, routingKey, user);
    }

    public void notify(String jsonObject, String exchange, String routingKey) {
        rabbitTemplate.convertAndSend(exchange, routingKey, jsonObject);
    }

}
