package io.github.api.domain.dto;

import io.github.api.domain.enums.OrderPayment;
import io.github.api.domain.enums.OrderStatus;

import java.math.BigDecimal;
import java.util.List;

public record OrderResponseDTO(
        String id,
        BigDecimal total,
        OrderPayment payment,
        OrderStatus status,
        List<ItemProductResponseDTO> itens
) {
}
