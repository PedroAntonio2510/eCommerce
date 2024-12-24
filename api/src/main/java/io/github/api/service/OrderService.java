package io.github.api.service;

import io.github.api.domain.Order;
import io.github.api.domain.enums.OrderStatus;
import io.github.api.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public Order saveOrder(Order order){
        if (order == null || order.getItens() == null || order.getItens().isEmpty()) {
            throw new IllegalArgumentException("Pedido inválido");
        }

        // Mapear os itens do pedido
        order.getItens().forEach(item -> {
            item.setOrder(order);
        });

        BigDecimal total = order.getItens().stream()
                .map(item -> item.getProduct().getPrice().multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        Integer totalQuantity = order.getItens().stream()
                        .mapToInt(item -> item.getQuantity())
                                .sum();
        order.setTotal(total);
        order.setQuantity(totalQuantity);
        order.setStatus(OrderStatus.PENDING);

        return orderRepository.save(order);

    }
}
