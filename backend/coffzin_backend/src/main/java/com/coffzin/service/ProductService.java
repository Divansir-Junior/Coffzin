package com.coffzin.service;

import java.util.List;

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

    public List <ProductResponseDTO> listProducts () {
        return productRepository.findAll()
                .stream()
                .map(ProductResponseDTO::fromEntity)
                .toList();
    }

    public List <ProductResponseDTO> searchProductByName (String name) {
        return productRepository.findByName(name)
                .map(ProductResponseDTO::fromEntity)
                .stream()
                .toList();
    }

    public List <ProductResponseDTO> searchProductByDescription (String description ) {
        return productRepository.findAll()
                .stream()
                .filter(product -> product.getDescription().toLowerCase().contains(description.toLowerCase()))
                .map(ProductResponseDTO::fromEntity)
                .toList();
    }

    public List <ProductResponseDTO> searchByPrice (Double price) {
        return productRepository.findAll()
                .stream()
                .filter(product -> product.getPrice().equals(price))
                .map(ProductResponseDTO::fromEntity)
                .toList();
    }

}
