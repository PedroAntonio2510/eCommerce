package io.github.api.service;

import io.github.api.domain.Order;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RabbitMqNotificationService {

    private RabbitTemplate rabbitTemplate;

    public void notificateOrderCreated(Order order, String exchange){
        rabbitTemplate.convertAndSend(exchange, "order-created", order);
    }

    public void notificateOrderUpdate(Order order, String exchange) {
        rabbitTemplate.convertAndSend(exchange, "order-update", order);
    }
}
