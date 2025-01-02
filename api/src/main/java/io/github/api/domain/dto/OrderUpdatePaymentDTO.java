package io.github.api.domain.dto;

import io.github.api.domain.enums.OrderPayment;
import jakarta.validation.constraints.NotNull;

public record OrderUpdatePaymentDTO(
        @NotNull(message = "You must provide a payment method")
        OrderPayment payment
) {
}
