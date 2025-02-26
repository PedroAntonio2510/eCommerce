package io.github.api.validator;

import io.github.api.domain.Product;
import io.github.api.domain.User;
import io.github.api.domain.exceptions.ObjectDuplicateException;
import io.github.api.repositories.ProductRepository;
import io.github.api.validator.productStrategy.ProductValidationStrategy;
import io.github.api.validator.userStrategy.UserValidationStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductValidator {

    private final Map<String, ProductValidationStrategy> mapStrategy;


    public boolean isProductValid(Product product, String strategy) {
        boolean strategyImpl = mapStrategy.get(strategy).validate(product);
        if (strategyImpl) {
            return strategyImpl;
        }
        throw new ObjectDuplicateException("Produto ja existe");
    }

//    private boolean existsProduct(Product product) {
//        Optional<Product> productOptional = repository
//                .findProductByNameEqualsIgnoreCase(product.getName());
//        if (product.getId() == null){
//            return productOptional.isPresent();
//        }
//        return productOptional
//                .map(Product::getId)
//                .stream()
//                .anyMatch(id -> !id.equals(product.getId()));
//    }
}
