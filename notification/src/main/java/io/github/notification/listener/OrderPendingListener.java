package io.github.notification.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadopago.resources.payment.Payment;
import io.github.notification.service.SESNotificationService;
import io.github.notification.service.SNSNotificationService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class OrderPendingListener {

    private final SNSNotificationService snsNotificationService;
    private final SESNotificationService sesNotificationService;
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    @Value("${rabbitmq.exchange.notification}")
    private String notificationExchange;

    public OrderPendingListener(SNSNotificationService snsNotificationService,
                                SESNotificationService sesNotificationService,
                                RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.snsNotificationService = snsNotificationService;
        this.sesNotificationService = sesNotificationService;
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = "${rabbitmq.queue.pending}")
    public void orderUpdate(String qrCode) {

        sesNotificationService.notificateSES(qrCode);
    }


}
