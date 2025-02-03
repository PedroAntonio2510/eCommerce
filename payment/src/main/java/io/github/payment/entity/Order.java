package io.github.payment.entity;


import io.github.payment.enums.OrderPayment;
import io.github.payment.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class Order {

    private String id;

    private List<ItemProduct> itens;

    private Integer quantity;

    private LocalDate orderDate;

    private LocalDate lastUpdate;

    private BigDecimal total;

    private OrderPayment payment;

    private OrderStatus status;

    private User user;

    private boolean integrity;

}
