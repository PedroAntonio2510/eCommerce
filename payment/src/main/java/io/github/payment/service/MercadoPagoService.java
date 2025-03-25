package io.github.payment.service;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.common.IdentificationRequest;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.payment.PaymentCreateRequest;
import com.mercadopago.client.payment.PaymentPayerPhoneRequest;
import com.mercadopago.client.payment.PaymentPayerRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;
import io.github.payment.domain.Order;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class MercadoPagoService {

    @Value("${mercado.pago.accessToken}")
    private String ACCESS_TOKEN;

    PaymentClient client = new PaymentClient();

    public Payment createPixPayment(Order order) throws MPException, MPApiException {
        MercadoPagoConfig.setAccessToken(ACCESS_TOKEN);

        String description = order.getItens().stream()
                .map(
                itemProduct -> itemProduct.getProduct().getName() + ": " + itemProduct.getProduct().getPrice()
                )
                .collect(Collectors.joining(", "));

        PaymentPayerRequest payer = PaymentPayerRequest.builder()
                .email("teste@gmail.com")
                .identification(IdentificationRequest.builder()
                        .type("CPF")
                        .number("87927765078")
                        .build())
                .phone(PaymentPayerPhoneRequest.builder()
                        .areaCode("55")
                        .number("1234556")
                        .build())
                .firstName("Teste")
                .lastName("usuario")
                .build();

        PaymentCreateRequest createRequest =
                PaymentCreateRequest.builder()
                        .transactionAmount(order.getTotal())
                        .description(description)
                        .paymentMethodId("pix")
                        .payer(payer)
                        .build();


        Payment payment = client.create(createRequest);
        return payment;
    }




}
