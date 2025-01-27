package io.github.api.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "tb_product")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column
    private String name;

    @Column
    private String description;

    @Column(scale = 2, precision = 10)
    private BigDecimal price;



}
