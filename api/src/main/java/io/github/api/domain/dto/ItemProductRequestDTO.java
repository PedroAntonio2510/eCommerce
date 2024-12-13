package io.github.api.domain.dto;

import jakarta.validation.constraints.NotNull;

public record ItemProductRequestDTO(
        @NotNull(message = "The product id can't be null")
        String productId,

        @NotNull(message = "The product quantity can't be null")
        Integer quantity
) {
}
