package io.github.notification.listener;

import io.github.notification.domain.Order;
import io.github.notification.messages.DefaultMessages;
import io.github.notification.service.SESNotificationService;
import io.github.notification.service.SNSNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class OrderCreateListener {

    private final SNSNotificationService snsNotificationService;
    private final SESNotificationService sesNotificationService;

    public OrderCreateListener(SNSNotificationService snsNotificationService, SESNotificationService sesNotificationService) {
        this.snsNotificationService = snsNotificationService;
        this.sesNotificationService = sesNotificationService;
    }

    @RabbitListener(queues = "${rabbitmq.queue.order.created}")
    public void orderCreated(Order order) {
        String message = String.format(DefaultMessages.ORDER_CREATED, order.getStatus().toString());

        snsNotificationService.notificateSNS(order.getUser().getPhoneNumber(), message);
        sesNotificationService.notificateSES(order.getUser().getEmail());
    }

    @RabbitListener(queues = "${rabbitmq.queue.order.update}")
    public void orderUpdate(Order order) {
        String message = String.format(DefaultMessages.ORDER_UPDATE,
                order.getStatus().toString());

        snsNotificationService.notificateSNS(order.getUser().getPhoneNumber(), message);
        sesNotificationService.notificateSES(order.getUser().getEmail());
    }
}
