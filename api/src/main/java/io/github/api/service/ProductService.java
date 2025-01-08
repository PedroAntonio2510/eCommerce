package io.github.api.service;

import io.github.api.domain.Product;
import io.github.api.repositories.ItemProductRepositoy;
import io.github.api.repositories.ProductRepository;
import io.github.api.repositories.specs.ProductSpecs;
import io.github.api.validator.ProductValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;
    private final ItemProductRepositoy itemProductRepositoy;
    private final ProductValidator validator;

    public Product saveProduct(Product product) {
        validator.validate(product);
        return repository.save(product);
    }

    public Optional<Product> getProductById(String id) {
        if (id.isEmpty() || id == null) {
            throw new IllegalArgumentException("The id isn't valid");
        }
        return repository.findById(id);
    }


    public List<Product> searchProducts(String name,
                                        BigDecimal price) {
        Specification<Product> specs = Specification.where(
                (root, query, cb) -> cb.conjunction()
        );
        if (name != null) {
            specs = specs.and(ProductSpecs.nameLike(name));
        }
        if (price != null) {
            specs = specs.and(ProductSpecs.priceLessThan(price));
        }
        return repository.findAll(specs);
    }

    public void deleteProduct(String id) {
        repository.deleteById(id);
    }
}


