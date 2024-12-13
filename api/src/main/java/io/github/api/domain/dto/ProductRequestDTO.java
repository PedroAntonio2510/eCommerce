package io.github.api.domain.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductRequestDTO(
        @NotNull(message = "The product name can't be null")
        String name,

        String description,

        @NotNull(message = "The product price can't be null")
        BigDecimal price
) {
}
