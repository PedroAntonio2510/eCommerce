package io.github.notification.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Product {

    private String id;

    private String name;

    private String description;

    private BigDecimal price;

}
