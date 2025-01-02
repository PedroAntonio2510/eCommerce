package io.github.api.service;

import io.github.api.domain.Order;
import io.github.api.domain.enums.OrderStatus;
import io.github.api.repositories.OrderRepository;
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

    @Value("${rabbitmq.order.exchange}")
    private String orderNotificationExchange;

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
        return orderRepository.findAll();
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
