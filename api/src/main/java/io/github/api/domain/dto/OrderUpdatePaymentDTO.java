package io.github.api.domain.dto;

import io.github.api.domain.enums.OrderPayment;

public record OrderUpdatePaymentDTO(
        OrderPayment payment
) {
}
