package io.github.notification.listener;

import io.github.notification.domain.Order;
import io.github.notification.messages.DefaultMessages;
import io.github.notification.service.SESNotificationService;
import io.github.notification.service.SNSNotificationService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class OrderPendingListener {

    private final SNSNotificationService snsNotificationService;
    private final SESNotificationService sesNotificationService;

    public OrderPendingListener(SNSNotificationService snsNotificationService, SESNotificationService sesNotificationService) {
        this.snsNotificationService = snsNotificationService;
        this.sesNotificationService = sesNotificationService;
    }
//
//    @RabbitListener(queues = "${rabbitmq.queue.pending}")
//    public void orderUpdate(Order order) {
//        String messageSES = String.format(DefaultMessages.HTMLBODY,
//                order.getStatus().toString());
//
//        String message = String.format(DefaultMessages.ORDER_UPDATE,
//                    order.getStatus().toString());
//
//        snsNotificationService.notificateSNS(order.getUser().getPhoneNumber(), message);
//        sesNotificationService.notificateSES(order.getUser().getEmail(), messageSES);
//    }


}
