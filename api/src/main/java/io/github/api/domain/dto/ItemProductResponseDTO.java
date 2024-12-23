package io.github.api.domain.dto;

import java.math.BigDecimal;

public record ItemProductResponseDTO(
        String productName,
        BigDecimal productPrice,
        Integer quantity
) {
}
