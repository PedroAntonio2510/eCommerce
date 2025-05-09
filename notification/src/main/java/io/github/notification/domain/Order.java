package io.github.notification.domain;


import io.github.notification.domain.enums.PaymentType;
import io.github.notification.domain.enums.OrderStatus;

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

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setItens(List<ItemProduct> itens) {
        this.itens = itens;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public void setLastUpdate(LocalDate lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public void setPayment(PaymentType payment) {
        this.payment = payment;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setIntegrity(boolean integrity) {
        this.integrity = integrity;
    }
}
