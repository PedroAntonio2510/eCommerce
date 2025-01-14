package io.github.api.scheduler;

import io.github.api.domain.Order;
import io.github.api.repositories.OrderRepository;
import io.github.api.service.RabbitMqNotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Component
public class SchedulerOrderWithoutIntegrity {

    // Quando possuir um pedido com integridade false,
    // um pedido feito quando o rabbitMq nao esta funcionando,
    // e quando voltar a funcionar ele notifica as filas e atualiza os pedidos

    private final OrderRepository orderRepository;
    private final RabbitMqNotificationService notificationService;
    private final String exchange;
    private final Logger logger = LoggerFactory.getLogger(SchedulerOrderWithoutIntegrity.class);

    public SchedulerOrderWithoutIntegrity(OrderRepository orderRepository,
                                          RabbitMqNotificationService notificationService,
                                          @Value("${rabbitmq.order.exchange}") String exchange) {
        this.orderRepository = orderRepository;
        this.notificationService = notificationService;
        this.exchange = exchange;
    }

    @Transactional
    @Scheduled(fixedDelay = 10, timeUnit = TimeUnit.SECONDS)
    public void searchOrderWithoutIntegratity() {
        orderRepository.findAllByIntegrityIsFalse().forEach(order -> {
            try {
                notificationService.orderCreatedNotification(order, exchange);
                updateOrder(order);
            } catch (RuntimeException ex) {
                logger.error(ex.getMessage());
            }
        });
    }

    public void updateOrder(Order order) {
        order.setIntegrity(true);
        orderRepository.save(order);
    }
}
