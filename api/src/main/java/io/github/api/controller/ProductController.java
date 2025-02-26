package io.github.api.controller;

import io.github.api.domain.Product;
import io.github.api.domain.dto.ProductRequestDTO;
import io.github.api.domain.mapper.ProductMapper;
import io.github.api.service.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@Tag(name = "Product Controller")
public class ProductController implements GenericController{

    private final ProductService service;
    private final ProductMapper productMapper;

    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid ProductRequestDTO dto){
        Product product = productMapper.toEntity(dto);
        URI uri = headerLocation(product.getId());
        service.saveProduct(product);
        return ResponseEntity.created(uri).build();
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'USER')")
    @GetMapping
    public ResponseEntity<Page<Product>> search(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) BigDecimal price,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize
            ){
        Page<Product> productList = service.searchProducts(name, price, pageNo, pageSize);
        return ResponseEntity.ok(productList);
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable String id,
                                    @RequestBody @Valid ProductRequestDTO dto){
        return service.getProductById(id)
                .map(product -> {
                    Product newProduct = productMapper.toEntity(dto);
                    product.setName(newProduct.getName());
                    product.setPrice(newProduct.getPrice());
                    product.setDescription(newProduct.getDescription());

                    service.saveProduct(product);
                    return ResponseEntity.noContent().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('MANAGER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id){
        Optional<Product> productOptional = service.getProductById(id);
        service.deleteProduct(productOptional.get().getId());
        return ResponseEntity.noContent().build();
    }



}
