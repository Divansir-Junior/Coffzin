package com.coffzin.service;

import org.springframework.stereotype.Service;

import com.coffzin.dto.request.ProductRequestDTO;
import com.coffzin.dto.response.ProductResponseDTO;
import com.coffzin.model.Product;
import com.coffzin.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ProductResponseDTO createProduct(ProductRequestDTO request) {
        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getStockQuantity());

        return ProductResponseDTO.fromEntity(productRepository.save(product));
        
    }


}
