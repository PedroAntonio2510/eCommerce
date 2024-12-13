package io.github.api.repositories;

import io.github.api.domain.ItemProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemProductRepositoy extends JpaRepository<ItemProduct, String> {

    Optional<ItemProduct> findItemProductByProduct_Name(String name);
}
