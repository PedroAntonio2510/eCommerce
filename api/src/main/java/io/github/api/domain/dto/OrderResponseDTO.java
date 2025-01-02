package io.github.api.domain.dto;

import io.github.api.domain.enums.OrderPayment;
import io.github.api.domain.enums.OrderStatus;

import java.time.LocalDate;
import java.util.List;

public record OrderResponseDTO(
        String id,
        LocalDate orderDate,
        LocalDate lastUpdate,
        String totalFmt,
        OrderPayment payment,
        OrderStatus status,
        List<ItemProductResponseDTO> itens
) {
}
