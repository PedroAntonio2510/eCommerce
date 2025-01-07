package io.github.notification.service;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.PublishRequest;
import org.springframework.stereotype.Service;

@Service
public class SnsNotificationService {

    private final AmazonSNS amazonSNS;

    public SnsNotificationService(AmazonSNS amazonSNS) {
        this.amazonSNS = amazonSNS;
    }

    public void notificate(String phoneNumber, String message) {
        PublishRequest publishRequest = new PublishRequest()
                .withMessage(message)
                .withPhoneNumber(phoneNumber);

        amazonSNS.publish(publishRequest);
    }

}
