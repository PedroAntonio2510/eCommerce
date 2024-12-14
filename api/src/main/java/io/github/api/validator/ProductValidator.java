package io.github.api.validator;

import io.github.api.domain.Product;
import io.github.api.exceptions.ObjectDuplicateException;
import io.github.api.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductValidator {

    private final ProductRepository repository;

    public void validate(Product product){
        if(existsProduct(product)){
            throw new ObjectDuplicateException("The Product " + product.getName() + " is registered on the database");
        }
    }

    private boolean existsProduct(Product product) {
        Optional<Product> productOptional = repository
                .findProductByNameEqualsIgnoreCase(product.getName());
        if (product.getId() == null){
            return productOptional.isPresent();
        }
        return productOptional
                .map(Product::getId)
                .stream()
                .anyMatch(id -> !id.equals(product.getId()));
    }
}
