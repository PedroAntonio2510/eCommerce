package io.github.payment.service;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.common.IdentificationRequest;
import com.mercadopago.client.payment.*;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.net.MPResultsResourcesPage;
import com.mercadopago.net.MPSearchRequest;
import com.mercadopago.resources.payment.Payment;
import io.github.payment.domain.Order;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
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

    public MPResultsResourcesPage<Payment> buscarPagemntos() throws MPException, MPApiException {
        MercadoPagoConfig.setAccessToken(ACCESS_TOKEN);
        PaymentClient client = new PaymentClient();

        Map<String, Object> filters = new HashMap<>();
        filters.put("sort", "date_created");
        filters.put("criteria", "desc");
//        filters.put("external_reference", "ID_REF");
        filters.put("range", "date_created");
        filters.put("begin_date", "NOW-30DAYS");
        filters.put("end_date", "NOW");

        MPSearchRequest searchRequest =
                MPSearchRequest.builder().offset(0).limit(30).filters(filters).build();
//        LOGGER.info(client.search(searchRequest).getResponse().toString());

        return client.search(searchRequest);
    }


}
