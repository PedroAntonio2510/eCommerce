package io.github.api.domain.mapper;

import io.github.api.domain.ItemProduct;
import io.github.api.domain.dto.ItemProductRequestDTO;
import io.github.api.domain.dto.ItemProductResponseDTO;
import io.github.api.repositories.ProductRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = ProductMapper.class)
public abstract class ItemProductMapper {

    @Autowired
    ProductRepository productRepository;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "order", ignore = true)
    @Mapping(target = "product", expression = "java( productRepository.findById(dto.productId()).orElse(null) )")
    public abstract ItemProduct toEntity(ItemProductRequestDTO dto);

    @Mapping(target = "productName", source = "product.name")
    public abstract ItemProductResponseDTO txoDTO(ItemProduct itemProduct);
}
