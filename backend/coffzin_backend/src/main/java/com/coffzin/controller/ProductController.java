package com.coffzin.controller;

import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coffzin.dto.request.ProductRequestDTO;
import com.coffzin.dto.request.UserRequestDTO;
import com.coffzin.dto.response.ProductResponseDTO;
import com.coffzin.dto.response.UserResponseDTO;
import com.coffzin.model.Product;
import com.coffzin.repository.ProductRepository;
import com.coffzin.service.ProductService;
import com.coffzin.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") 
public class ProductController {

   private final ProductService productService;

   @Operation(summary = "Create a new product")
   @ApiResponses(value = {
       @ApiResponse(responseCode = "200", description = "Product created successfully"),
       @ApiResponse(responseCode = "400", description = "Invalid input data")
   })
   @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(
            @RequestBody ProductRequestDTO request
    ) {
        return ResponseEntity.ok(productService.createProduct(request));
    }

    @Operation(summary = "Get a product by ID")  
    @GetMapping("/{id}")
    public ResponseEntity <ProductResponseDTO> getProductById (@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }
  
    @Operation(summary = "Delete a product by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity <Void> deleteProduct (@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Update a product by ID")
    @PutMapping("/{id}")
    public ResponseEntity <ProductResponseDTO> updateProduct (@PathVariable Long id, @RequestBody ProductRequestDTO request) {
        return ResponseEntity.ok(productService.updateProduct(id, request));
    }

    @Operation(summary = "List all products")
    @GetMapping
    public List <ProductResponseDTO> listAll () {
        return productService.listProducts()
    }
}
