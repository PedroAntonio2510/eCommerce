package io.github.api.controller;

import io.github.api.domain.ItemProduct;
import io.github.api.domain.dto.ItemProductRequestDTO;
import io.github.api.domain.dto.ItemProductResponseDTO;
import io.github.api.domain.mapper.ItemProductMapper;
import io.github.api.service.ItemProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/itemproduct")
@RequiredArgsConstructor
public class ItemProductController implements GenericController{

    private final ItemProductService service;
    private final ItemProductMapper itemProductMapper;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid ItemProductRequestDTO dto) {
        ItemProduct item = itemProductMapper.toEntity(dto);
        URI uri = headerLocation(item.getId());
        service.saveItem(item);
        return ResponseEntity.created(uri).build();
    }

    @GetMapping
    public ResponseEntity<List<ItemProductResponseDTO>> getAll() {
        List<ItemProduct> items = service.getAllItens();
        List<ItemProductResponseDTO> response = items.stream()
                .map(itemProductMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }


}
