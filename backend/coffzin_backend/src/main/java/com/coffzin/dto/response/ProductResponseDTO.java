package com.coffzin.dto.response;

import com.coffzin.model.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Dados publicos de produto.")
public class ProductResponseDTO {

    @Schema(description = "ID interno do produto.", example = "1")
    private Long id;

    @Schema(description = "Nome do produto.", example = "Vanilla Latte")
    private String name;

    @Schema(description = "Descricao do produto.", example = "Cafe espresso com leite vaporizado e baunilha.")
    private String description;

    @Schema(description = "Preco unitario.", example = "18.9")
    private Double price;

    @Schema(description = "Quantidade em estoque.", example = "25")
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
