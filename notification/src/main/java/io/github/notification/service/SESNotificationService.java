package io.github.notification.service;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.*;
import com.mercadopago.resources.payment.Payment;
import io.github.notification.domain.User;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
public class SESNotificationService {

    private final AmazonSimpleEmailService amazonSES;
    private final SpringTemplateEngine templateEngine;

    public SESNotificationService(AmazonSimpleEmailService amazonSES,
                                  SpringTemplateEngine templateEngine) {
        this.amazonSES = amazonSES;
        this.templateEngine = templateEngine;
    }

    private String verifyURL = "http://localhost:8080/user/verify?code=";

    static final String SUBJECT = "Amazon SES test (AWS SDK for Java)";

    static final String FROM = "antonio.pedro25@outlook.com";

    static final String TEXTBODY = "Your order was realized, stay alert on email for updates. ";

    public void notificateSES(String email, String message) {
        SendEmailRequest request = new SendEmailRequest()
                .withDestination(new Destination().withToAddresses(email))
                .withMessage(new Message()
                        .withBody(new Body()
                                .withHtml(new Content()
                                        .withCharset("UTF-8").withData(message))
                                .withText(new Content()
                                        .withCharset("UTF-8").withData(TEXTBODY)))
                        .withSubject(new Content()
                                .withCharset("UTF-8").withData(SUBJECT)))
                .withSource(FROM);
        amazonSES.sendEmail(request);
    }

    public void notificateSES(User user) {
        Context context =  new Context();
        context.setVariable("name", user.getName());
        context.setVariable("url", verifyURL + user.getVerificationCode());

        String content = templateEngine.process("email-verification", context);

        SendEmailRequest request = new SendEmailRequest()
                .withDestination(new Destination().withToAddresses(user.getEmail()))
                .withMessage(new Message()
                        .withBody(new Body()
                                .withHtml(new Content()
                                        .withCharset("UTF-8").withData(content)))
                        .withSubject(new Content()
                                .withCharset("UTF-8").withData(SUBJECT)))
                .withSource(FROM);
        amazonSES.sendEmail(request);
    }

    public void notificateSES(String qrCode) {
        Context context = new Context();
        context.setVariable("qrCode", qrCode);

        String content = templateEngine.process("email-pixpending", context);

        SendEmailRequest request = new SendEmailRequest()
                .withDestination(new Destination().withToAddresses("pedrinhodeccache@gmail.com"))
                .withMessage(new Message()
                        .withBody(new Body()
                                .withHtml(new Content()
                                        .withCharset("UTF-8").withData(content)))
                        .withSubject(new Content()
                                .withCharset("UTF-8").withData(SUBJECT)))
                .withSource(FROM);
        amazonSES.sendEmail(request);
    }



}
