package io.github.notification.listener;

import io.github.notification.domain.Order;
import io.github.notification.messages.DefaultMessages;
import io.github.notification.service.SnsNotificationService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class OrderCreateListener {

    private final SnsNotificationService notificationService;

    public OrderCreateListener(SnsNotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @RabbitListener(queues = "${rabbitmq.queue.order.created}")
    public void orderCreated(Order order) {
        String message = String.format(DefaultMessages.ORDER_CREATED, order.getStatus().toString());

        notificationService.notificate(order.getUser().getPhoneNumber(), message);
    }

    @RabbitListener(queues = "${rabbitmq.queue.order.update}")
    public void orderUpdate(Order order) {
        String message = String.format(DefaultMessages.ORDER_UPDATE,
                order.getStatus().toString());

        notificationService.notificate(order.getUser().getPhoneNumber(), message);
    }
}
