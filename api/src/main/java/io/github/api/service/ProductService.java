package io.github.api.service;

import io.github.api.domain.Product;
import io.github.api.repositories.ProductRepository;
import io.github.api.repositories.specs.ProductSpecs;
import io.github.api.validator.ProductValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;
    private final ProductValidator validator;

    public Product saveProduct(Product product) {
        validator.isProductValid(product,"product_existing");
        return repository.save(product);
    }

    public Optional<Product> getProductById(String id) {
        if (id.isEmpty() || id == null) {
            throw new IllegalArgumentException("The id isn't valid");
        }
        return repository.findById(id);
    }


    public Page<Product> searchProducts(String name,
                                        BigDecimal price,
                                        int pageNo,
                                        int pageSize) {
        Specification<Product> specs = Specification.where(
                (root, query, cb) -> cb.conjunction()
        );
        if (name != null) {
            specs = specs.and(ProductSpecs.nameLike(name));
        }
        if (price != null) {
            specs = specs.and(ProductSpecs.priceLessThan(price));
        }
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return repository.findAll(specs, pageable);
    }

    public void deleteProduct(String id) {
        repository.deleteById(id);
    }

}


