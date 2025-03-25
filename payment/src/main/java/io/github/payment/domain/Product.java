package io.github.payment.domain;

import java.math.BigDecimal;

public class Product {

    private String id;

    private String name;

    private String description;

    private BigDecimal price;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
