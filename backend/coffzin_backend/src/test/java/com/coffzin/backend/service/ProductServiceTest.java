package com.coffzin.backend.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.coffzin.dto.request.ProductRequestDTO;
import com.coffzin.model.Product;
import com.coffzin.repository.ProductRepository;
import com.coffzin.service.ProductService;

@ExtendWith(MockitoExtension.class)
 class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Test
    void createProduct() {
        ProductRequestDTO request = ProductRequestDTO.builder()
        .name("Café Premium")
        .description("Café de alta qualidade, com sabor intenso e aroma marcante.")
        .stockQuantity(100)
        .price(19.99)
        .build();

        Product savedProduct = new Product();
        savedProduct.setId(1L);
        savedProduct.setName("Café Premium");
        savedProduct.setDescription("Café de alta qualidade, com sabor intenso e aroma marcante.");
        savedProduct.setPrice(19.99);
        savedProduct.setQuantity(100);

        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);
        productService.createProduct(request);

    }



}
