package io.github.api.validator.productStrategy;

import io.github.api.domain.Product;

public interface ProductValidationStrategy {
    boolean validate(Product product);
}
