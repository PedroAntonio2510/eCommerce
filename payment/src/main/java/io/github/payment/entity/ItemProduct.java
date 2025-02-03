package io.github.payment.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ItemProduct {

    private String id;

    private Product product;

    private Order order;


    private Integer quantity;
}
