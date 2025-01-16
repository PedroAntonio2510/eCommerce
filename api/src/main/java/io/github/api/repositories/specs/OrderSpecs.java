package io.github.api.repositories.specs;

import io.github.api.domain.Order;
import io.github.api.domain.enums.OrderStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;

@Slf4j
public class OrderSpecs {

    public static Specification<Order> dateLessThan(Integer days) {
        LocalDate date = LocalDate.now().minusDays(days);
        return ((root, query, cb) -> cb.lessThan(
                root.get("date"), date
        ));
    }

    public static Specification<Order> userEmailEqual(String email) {
        return (root, query, cb) -> cb.equal(root.get("user").get("email"), email);
    }

    public static Specification<Order> statusEqual(OrderStatus status) {
        return (root, query, cb) -> cb.equal(
                root.get("status"), status
        );
    }

    public static Specification<Order> totalLessThan(BigDecimal total) {
        return (root, query, cb) -> cb.lessThan(
                root.get("total"), total
        );
    }

    public static Specification<Order> createdWithinLastDays(Integer days) {
        LocalDate today = LocalDate.now();
        LocalDate pastDate = today.minusDays(days);
        log.info("Filtrando pedidos de: " + pastDate + " até " + today);
        return (root, query, cb) -> cb.between(
                root.get("orderDate"),
                pastDate,
                today
        );
    }

    public static Specification<Order> createdInLast7Days() {
        LocalDate today = LocalDate.now();
        LocalDate pastDate = today.minusDays(7);
        log.info("Filtrando pedidos de: " + pastDate + " até " + today);
        return (root, query, cb) -> cb.between(
                root.get("orderDate"),
                pastDate,
                today
        );
    }


//    public static Specification<Order> createdWithinLast30Days() {
//        return (Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
//            LocalDate today = LocalDate.now();
//            LocalDate thirtyDaysAgo = today.minusDays(30);
//            return builder.between(root.get("createdAt"), thirtyDaysAgo, today);
//        };
//    }
}
