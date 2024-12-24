package io.github.api.domain.mapper;

import io.github.api.domain.Order;
import io.github.api.domain.dto.OrderRequestDTO;
import io.github.api.domain.dto.OrderResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = ItemProductMapper.class)
public interface OrderMapper {

    Order toEntityt(OrderRequestDTO dto);

    OrderResponseDTO toResponseDTO(Order order);

}
