package io.github.api.domain.dto;

import io.github.api.domain.Product;
import io.github.api.domain.enums.OrderPayment;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record OrderRequestDTO(
        @NotNull(message = "You must must provide a product to make a order")
        List<Product> itens,

        @NotNull(message = "You must provide a payment method")
        OrderPayment payment,

        @NotNull(message = "You must provide a quantity")
        Integer quantity
) {
}
