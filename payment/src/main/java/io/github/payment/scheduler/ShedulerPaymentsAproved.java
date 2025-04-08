package io.github.payment.scheduler;

import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.net.MPResultsResourcesPage;
import com.mercadopago.resources.payment.Payment;
import io.github.payment.service.MercadoPagoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Component
public class ShedulerPaymentsAproved {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShedulerPaymentsAproved.class);

    private final RabbitTemplate rabbitTemplate;
    private final MercadoPagoService mercadoPagoService;

    public ShedulerPaymentsAproved(RabbitTemplate rabbitTemplate, MercadoPagoService mercadoPagoService) {
        this.rabbitTemplate = rabbitTemplate;
        this.mercadoPagoService = mercadoPagoService;
    }

    @Transactional
    @Scheduled(fixedRate = 20, timeUnit = TimeUnit.SECONDS)
    public void searchPaymentsWithStatusAproved() throws MPException, MPApiException {
        MPResultsResourcesPage<Payment> paymentMPResultsResourcesPage = mercadoPagoService.buscarPagemntos();
        LOGGER.info(paymentMPResultsResourcesPage.getResponse().getStatusCode().toString());
        var resultsm = paymentMPResultsResourcesPage.getResults().stream()
                        .map(result -> result.getStatus().toString())
                                .toList();
        LOGGER.info(resultsm.toString());
        try {
            paymentMPResultsResourcesPage.getResults().forEach(result -> {
                if (result.getStatus().equals("approved")) {
                    rabbitTemplate.convertAndSend("payment.ex", "payment-paid", result);
                }
            });
        } catch (RuntimeException e) {
            LOGGER.error(e.getMessage());
        }
    }
}
