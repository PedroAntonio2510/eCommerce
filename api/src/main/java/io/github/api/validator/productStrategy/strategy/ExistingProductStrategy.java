package io.github.api.validator.productStrategy.strategy;

import io.github.api.domain.Product;
import io.github.api.repositories.ProductRepository;
import io.github.api.validator.productStrategy.ProductValidationStrategy;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("product_existing")
@AllArgsConstructor
public class ExistingProductStrategy implements ProductValidationStrategy {

    private final ProductRepository repository;

    @Override
    public boolean validate(Product product) {
        Optional<Product> productOptional = repository
                .findProductByNameEqualsIgnoreCase(product.getName());
        if (product.getId() == null){
            return productOptional.isEmpty();
        }
        return productOptional
                .map(Product::getId)
                .stream()
                .anyMatch(id -> !id.equals(product.getId()));
    }
}
