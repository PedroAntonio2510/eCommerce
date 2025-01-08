package io.github.api.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.github.api.domain.enums.OrderPayment;
import io.github.api.domain.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tb_order")
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<ItemProduct> itens;

    private Integer quantity;

    @CreatedDate
    private LocalDate orderDate;

    @LastModifiedDate
    private LocalDate lastUpdate;

    @Column(scale = 2, precision = 10)
    private BigDecimal total;

    @Enumerated(EnumType.STRING)
    private OrderPayment payment;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @ManyToOne
    @JoinColumn(name = "id_user_model")
    private UserModel user;

    private boolean integrity;

}
