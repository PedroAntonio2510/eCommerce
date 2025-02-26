package io.github.api.service;

import io.github.api.domain.ItemProduct;
import io.github.api.domain.Order;
import io.github.api.domain.User;
import io.github.api.domain.enums.OrderStatus;
import io.github.api.repositories.OrderRepository;
import io.github.api.security.SecurityService;
import io.github.api.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

import static io.github.api.repositories.specs.OrderSpecs.*;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final RabbitMqNotificationService notificationService;
    private final SecurityService securityService;
    private final UserValidator validator;

    @Value("${rabbitmq.order.notification.exchange}")
    private String notificationExchange;

    @Value("${rabbitmq.order.payment.exchange}")
    private String paymentExchange;

    @Value("order-created")
    private String routingOrderCreated;

    @Value("order-update")
    private String routingOrderUpdate;

    @Value("order-complete")
    private String routingOrderComplete;

    public Order saveOrder(Order order) {
        User user = securityService.getUserLogged();
        validator.isUserEnable(user);

        if (user == null) {
            throw new IllegalStateException("No user is logged in");
        }

        // Mapear os itens do pedido
        for (ItemProduct item : order.getItens()) {
            item.setOrder(order);
        }

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
        MessagePostProcessor messagePostProcessor = message -> {
            message.getMessageProperties().setPriority(priority.intValue());
            return message;
        };

        notifyRabbitMq(order, notificationExchange, routingOrderCreated, messagePostProcessor);
        notifyRabbitMq(order, paymentExchange, routingOrderCreated, messagePostProcessor);

        return savedOrder;
    }

    public Page<Order> listOrder(Integer days,
                                 OrderStatus status,
                                 BigDecimal totalFMT,
                                 int pageNo,
                                 int pageSize) {
        User user = securityService.getUserLogged();
        if (user != null) {
            Specification<Order> specs = Specification.where((root, query, cb) -> cb.conjunction());
            if (days != null) {
                specs = specs.and(createdWithinLastDays(days));
            }
            if (status != null) {
                specs = specs.and(statusEqual(status));
            }
            if (totalFMT != null) {
                specs = specs.and(totalLessThan(totalFMT));
            }
            Pageable pageable = PageRequest.of(pageNo, pageSize);
            return orderRepository.findAll(specs.and(userEmailEqual(user.getEmail())), pageable);
        }
        return Page.empty();
    }

    public Order updateOrder(Order order) {
        if (order.getId() == null) {
            throw new IllegalArgumentException("The order doesnt exists");
        }

        Order orderUpdated = orderRepository.save(order);

        if (orderUpdated.getStatus() == OrderStatus.DELIVERED) {
            notifyRabbitmq(orderUpdated, notificationExchange, routingOrderComplete);
        } else {
            notifyRabbitmq(orderUpdated, notificationExchange, routingOrderUpdate);
        }
        return orderUpdated;
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

    public Optional<Order> getOrderById(String id) {
        return orderRepository.findById(id);
    }

    public Order findOrderById(String id){
        return orderRepository.findOrderById(id);
    }

    public void notifyRabbitMq(Order order, String exchange, String routingKey, MessagePostProcessor messagePostProcessor) {
        try {
            notificationService.notify(order, exchange, routingKey, messagePostProcessor);
        } catch (RuntimeException e) {
            order.setIntegrity(false);
            orderRepository.save(order);
            e.printStackTrace();
        }
    }

    public void notifyRabbitmq(Order order, String exchange, String routingKey) {
        try {
            notificationService.notify(order, exchange, routingKey);
        } catch (RuntimeException e) {
            order.setIntegrity(false);
            orderRepository.save(order);
        }
    }

}
