package io.github.api.repositories;

import io.github.api.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, String> {

    List<Order> findAllByIntegrityIsFalse();

}
