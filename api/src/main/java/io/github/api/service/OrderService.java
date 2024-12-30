package io.github.api.service;

import io.github.api.domain.Order;
import io.github.api.domain.enums.OrderStatus;
import io.github.api.repositories.OrderRepository;
import io.github.api.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public Order saveOrder(Order order){

        // Mapear os itens do pedido
        order.getItens().forEach(item -> {
            item.setOrder(order);
        });

        BigDecimal total = order.getItens().stream()
                .map(item -> item.getProduct()
                        .getPrice()
                        .multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Integer totalQuantity = order.getItens()
                .stream()
                        .mapToInt(item -> item.getQuantity())
                                .sum();

        order.setTotal(total);
        order.setQuantity(totalQuantity);
        order.setStatus(OrderStatus.PENDING);

        return orderRepository.save(order);

    }

    public List<Order> listOrder() {
        return orderRepository.findAll();
    }
}
