package io.github.notification.listener;

import io.github.notification.domain.Order;
import io.github.notification.messages.DefaultMessages;
import io.github.notification.service.SESNotificationService;
import io.github.notification.service.SNSNotificationService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class OrderCreatedListener {

    private final SNSNotificationService snsNotificationService;
    private final SESNotificationService sesNotificationService;
    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange.notification}")
    private String notificationExchange;

    public OrderCreatedListener(SNSNotificationService snsNotificationService, SESNotificationService sesNotificationService, RabbitTemplate rabbitTemplate) {
        this.snsNotificationService = snsNotificationService;
        this.sesNotificationService = sesNotificationService;
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = "${rabbitmq.queue.created}")
    public void orderCreated(Order order) {
        String message = String.format(DefaultMessages.ORDER_CREATED,
                order.getStatus().toString());

        String messageSES = String.format(DefaultMessages.HTMLBODY,
                order.getStatus().toString());

        snsNotificationService.notificateSNS(order.getUser().getPhoneNumber(), message);
        sesNotificationService.notificateSES(order.getUser().getEmail(), messageSES);

        rabbitTemplate.convertAndSend(notificationExchange,"order-pending", order);
    }

}
