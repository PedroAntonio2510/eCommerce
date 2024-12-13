package io.github.api.repositories;

import io.github.api.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, String>, JpaSpecificationExecutor<Product> {
    Optional<Product> findProductByNameEqualsIgnoreCase(String name);
}
