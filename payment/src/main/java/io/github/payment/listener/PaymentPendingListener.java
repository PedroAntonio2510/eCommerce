package io.github.payment.listener;

import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;
import io.github.payment.domain.Order;
import io.github.payment.service.MercadoPagoService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class PaymentPendingListener {

    private final MercadoPagoService mercadoPagoService;
    private final RabbitTemplate rabbitTemplate;

    public PaymentPendingListener(MercadoPagoService mercadoPagoService, RabbitTemplate rabbitTemplate) {
        this.mercadoPagoService = mercadoPagoService;
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = "payment-pending.ms-payment")
    public void paymentPending(Order order) throws MPException, MPApiException {
        Payment pixPayment = mercadoPagoService.createPixPayment(order);
        rabbitTemplate.convertAndSend("notification.ex",
                "order-pending",
                pixPayment);

    }
}
