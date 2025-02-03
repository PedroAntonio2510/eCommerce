package io.github.notification.listener;

import io.github.notification.domain.Order;
import io.github.notification.messages.DefaultMessages;
import io.github.notification.service.SESNotificationService;
import io.github.notification.service.SNSNotificationService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class OrderCompleteListener {

    private final SNSNotificationService snsNotificationService;
    private final SESNotificationService sesNotificationService;

    public OrderCompleteListener(SNSNotificationService snsNotificationService, SESNotificationService sesNotificationService) {
        this.snsNotificationService = snsNotificationService;
        this.sesNotificationService = sesNotificationService;
    }

    @RabbitListener(queues = "${rabbitmq.queue.complete}")
    public void orderComplete(Order order){
        String messageSES = String.format(DefaultMessages.HTMLBODYCOMPLETE,
                order.getStatus().toString());

        String messageSNS = String.format(DefaultMessages.ORDER_COMPLETE,
                order.getStatus().toString());

        snsNotificationService.notificateSNS(order.getUser().getPhoneNumber(), messageSNS);
        sesNotificationService.notificateSES(order.getUser().getEmail(), messageSES);
    }


}
