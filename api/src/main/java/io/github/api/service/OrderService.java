package io.github.api.service;

import io.github.api.domain.Order;
import io.github.api.domain.User;
import io.github.api.domain.enums.OrderStatus;
import io.github.api.repositories.OrderRepository;
import io.github.api.security.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final RabbitMqNotificationService notificationService;
    private final SecurityService securityService;

    @Value("${rabbitmq.order.exchange}")
    private String orderNotificationExchange;

    public Order saveOrder(Order order){
        User user = securityService.getUserLogged();
        if (user == null) {
            throw new IllegalStateException("No user is logged in");
        }
        // Mapear os itens do pedido
        order.getItens().forEach(item -> {
            item.setOrder(order);
        });

        BigDecimal total = getTotal(order);

        Integer totalQuantity = getTotalQuantity(order);

        order.setTotal(total);
        order.setQuantity(totalQuantity);
        order.setStatus(OrderStatus.PENDING);
        order.setUser(user);

        Order savedOrder = orderRepository.save(order);

        // Define a prioridade de um pedido baseado no valor total
        BigDecimal priority = order.getTotal().compareTo(
                new BigDecimal(1000)) > 0
                ? new BigDecimal(10)
                : new BigDecimal(5);
        MessagePostProcessor messagePostProcessor =  message -> {
            message.getMessageProperties().setPriority(priority.intValue());
            return message;
        };

        notifyRabbitMq(order, messagePostProcessor);

        return savedOrder;

    }

    public List<Order> listOrder() {
        User user = securityService.getUserLogged();
        if (user != null) {
            return orderRepository.findByUserEmail(user.getEmail());
        }
        return List.of();
    }

    public Order updateOrder(Order order) {
        if (order.getId() == null) {
            throw new IllegalArgumentException("The order doesnt exists");
        }
        Order orderUpdated = orderRepository.save(order);

        notifyUpdateRabbitMq(orderUpdated);

        return orderUpdated;
    }

    public Optional<Order> getOrderById(String id) {
        return orderRepository.findById(id);
    }

    private BigDecimal getTotal(Order order) {
        BigDecimal total = order.getItens().stream()
                .map(item -> item.getProduct()
                        .getPrice()
                        .multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return total;
    }

    private Integer getTotalQuantity(Order order) {
        Integer totalQuantity = order.getItens()
                .stream()
                        .mapToInt(item -> item.getQuantity())
                                .sum();
        return totalQuantity;
    }

    public void notifyRabbitMq(Order order, MessagePostProcessor messagePostProcessor) {
        try {
            notificationService.orderCreatedNotification(order, orderNotificationExchange, messagePostProcessor);
        } catch (RuntimeException e) {
            order.setIntegrity(false);
            orderRepository.save(order);
        }
    }

    public void notifyUpdateRabbitMq(Order order) {
        try {
            notificationService.orderUpdateNotification(order, orderNotificationExchange);
        } catch (RuntimeException e) {
            order.setIntegrity(false);
            orderRepository.save(order);
        }
    }
}
