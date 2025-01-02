package io.github.api.domain.mapper;

import io.github.api.domain.Order;
import io.github.api.domain.dto.OrderRequestDTO;
import io.github.api.domain.dto.OrderResponseDTO;
import io.github.api.domain.dto.OrderUpdatePaymentDTO;
import io.github.api.domain.dto.OrderUpdateStatusDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.text.NumberFormat;

@Mapper(componentModel = "spring", uses = ItemProductMapper.class)
public interface OrderMapper {

    @Mapping(target = "integrity", constant = "true")
    Order toEntity(OrderRequestDTO dto);

    @Mapping(target = "totalFmt", expression = "java( setTotalFmt(order) )")
    OrderResponseDTO toResponseDTO(Order order);

    OrderRequestDTO toUpdatePayment(OrderUpdatePaymentDTO dto);

    Order toUpdateStatus(OrderUpdateStatusDTO dto);

    default String setTotalFmt(Order order) {
        return NumberFormat.getCurrencyInstance().format(order.getTotal().doubleValue());
    }
}
