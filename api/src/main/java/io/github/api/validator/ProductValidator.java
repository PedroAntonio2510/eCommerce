package io.github.api.validator;

import io.github.api.domain.Product;
import io.github.api.domain.exceptions.ObjectDuplicateException;
import io.github.api.validator.productStrategy.ProductValidationStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

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

}
