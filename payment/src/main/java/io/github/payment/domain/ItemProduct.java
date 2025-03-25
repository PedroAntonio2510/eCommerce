package io.github.payment.domain;

public class ItemProduct {

    private String id;

    private Product product;

    private Order order;

    private Integer quantity;

    public String getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public Order getOrder() {
        return order;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
