package io.github.api.domain.mapper;

import io.github.api.domain.Product;
import io.github.api.domain.dto.ProductRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "id", ignore = true)
    Product toEntity(ProductRequestDTO dto);


}
