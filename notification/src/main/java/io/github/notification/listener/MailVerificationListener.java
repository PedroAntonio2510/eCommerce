package io.github.notification.listener;

import io.github.notification.domain.User;
import io.github.notification.service.SESNotificationService;
import io.github.notification.service.SNSNotificationService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MailVerificationListener {

    private final SNSNotificationService snsNotificationService;
    private final SESNotificationService sesNotificationService;

    public MailVerificationListener(SNSNotificationService snsNotificationService,
                                    SESNotificationService sesNotificationService) {
        this.snsNotificationService = snsNotificationService;
        this.sesNotificationService = sesNotificationService;
    }

    @RabbitListener(queues = "${rabbitmq.queue.email-verification}")
    public void mailVerification(User user) {
        snsNotificationService.notificateSNS(user.getPhoneNumber(), "You need to verify your account to enjoy all the features from the eCommerceAPI");
        sesNotificationService.notificateSES(user);
    }
}

