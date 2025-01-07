package io.github.notification.domain;

import lombok.Getter;
import lombok.Setter;

;


@Getter
@Setter
public class ItemProduct {

    private String id;

    private Product product;

    private Order order;

    private Integer quantity;
}
