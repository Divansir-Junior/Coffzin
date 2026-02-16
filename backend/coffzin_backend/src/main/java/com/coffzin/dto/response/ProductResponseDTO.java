package com.coffzin.dto.response;

import com.coffzin.model.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDTO {

    private Long id;
    private String name;
    private String description;
    private Double price;
    private Integer stockQuantity;

    public static ProductResponseDTO fromEntity(Product product) {
        return new ProductResponseDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getQuantity()
        );
    }
}
