package io.github.api.repositories;

import io.github.api.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, String>, JpaSpecificationExecutor<Order> {

    Order findOrderById(String id);

    List<Order> findAllByIntegrityIsFalse();

    List<Order> findByUserEmail(String email);
}
