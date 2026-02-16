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

    public ProductResponseDTO getProductById(Long id) { 
        return productRepository.findById(id).
                map(ProductResponseDTO::fromEntity)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }

    public void deleteProduct (Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found with id: " + id);
        }

        productRepository.deleteById(id);
    }

    public ProductResponseDTO updateProduct (Long id, ProductRequestDTO request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getStockQuantity());

        return ProductResponseDTO.fromEntity(productRepository.save(product));
    }

}
