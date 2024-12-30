package io.github.api.domain.dto;

import io.github.api.domain.enums.OrderStatus;

public record OrderUpdateStatusDTO(
        OrderStatus status
) {
}
