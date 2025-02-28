package io.github.api.domain.dto;

import io.github.api.domain.enums.PaymentType;
import io.github.api.domain.enums.OrderStatus;

import java.time.LocalDate;
import java.util.List;

public record OrderResponseDTO(
        String id,
        LocalDate orderDate,
        LocalDate lastUpdate,
        String totalFmt,
        PaymentType payment,
        OrderStatus status,
        List<ItemProductResponseDTO> itens
) {
}
