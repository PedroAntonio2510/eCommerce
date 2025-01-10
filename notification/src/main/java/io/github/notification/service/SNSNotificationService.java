package io.github.notification.service;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.PublishRequest;
import org.springframework.stereotype.Service;

@Service
public class SNSNotificationService {

    private final AmazonSNS amazonSNS;

    public SNSNotificationService(AmazonSNS amazonSNS) {
        this.amazonSNS = amazonSNS;
    }

    public void notificateSNS(String phoneNumber, String message) {
        PublishRequest publishRequest = new PublishRequest()
                .withMessage(message)
                .withPhoneNumber(phoneNumber);

        amazonSNS.publish(publishRequest);
    }

}
