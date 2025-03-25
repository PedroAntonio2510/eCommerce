package io.github.payment.domain;

import io.github.payment.domain.enums.OrderStatus;
import io.github.payment.domain.enums.PaymentType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class Order {

    private String id;

    private List<ItemProduct> itens;

    private Integer quantity;

    private LocalDate orderDate;

    private LocalDate lastUpdate;

    private BigDecimal total;

    private PaymentType payment;

    private OrderStatus status;

    private User user;

    private boolean integrity;

    public String getId() {
        return id;
    }

    public List<ItemProduct> getItens() {
        return itens;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public LocalDate getLastUpdate() {
        return lastUpdate;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public PaymentType getPayment() {
        return payment;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public User getUser() {
        return user;
    }

    public boolean isIntegrity() {
        return integrity;
    }
}
