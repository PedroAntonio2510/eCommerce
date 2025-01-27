package io.github.api.service;

import io.github.api.domain.Product;
import io.github.api.domain.exceptions.ObjectDuplicateException;
import io.github.api.repositories.ProductRepository;
import io.github.api.validator.ProductValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository repository;

    @Mock
    private ProductValidator validator;

    @InjectMocks
    private ProductService service;

    private Product product;

    @BeforeEach
    void setUp() {

        product = new Product(
                "fd430305-635c-4d90-8d02-0f50523576fb",
                "Teclado",
                "",
                new BigDecimal("100.00")
        );
    }

    @Test
    @DisplayName("Must save a product")
    void testGivenProductObject_WhenSaveProduct_thenReturnProduct() {

        given(repository.save(product)).willReturn(product);

        Product savedProduct = service.saveProduct(product);

        assertNotNull(savedProduct);
        assertEquals("Teclado", product.getName());
        assertEquals("", product.getDescription());
        assertEquals(new BigDecimal("100.00"), product.getPrice());
    }

    @Test
    @DisplayName("Must throw an exception when trying to save a product with a name already registered")
    void testGivenExistingProductName_WhenSaveProduct_thenThrowsException() {

        doThrow(ObjectDuplicateException.class)
                .when(validator).validate(any(Product.class));

        assertThrows(ObjectDuplicateException.class,
                () -> service.saveProduct(product)
        );

        verify(repository, never()).save(any(Product.class));
    }

    @Test
    @DisplayName("Must return a product object when a valid id is passed")
    void testGivenProductId_WhenGetProductById_thenReturnProductObject() {

        given(repository.findById(anyString())).willReturn(Optional.of(product));
        Optional<Product> savedProduct = service.getProductById("fd430305-635c-4d90-8d02-0f50523576fb");

        assertNotNull(savedProduct);
        assertEquals("Teclado", savedProduct.get().getName());
    }

    @Test
    @DisplayName("Must throw an exception when a invalid id is passed")
    void testGivenInvalidProductId_WhenGetProductById_thenReturnAnException() {
        assertThrows(IllegalArgumentException.class, () -> service.getProductById(""));
    }


}