package com.coffzin.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Payload usado para criar ou atualizar produto.")
public class ProductRequestDTO {

    @Schema(description = "Nome comercial do produto.", example = "Vanilla Latte")
    @NotBlank(message = "Name is required")
    private String name;

    @Schema(description = "Descricao exibida para o cliente.", example = "Cafe espresso com leite vaporizado e baunilha.")
    @NotBlank(message = "Description is required")
    private String description;

    @Schema(description = "Preco unitario em reais.", example = "18.90", minimum = "0.01")
    @NotNull(message = "Price is required")
    @Positive(message = "Price must be greater than zero")
    private Double price;

    @Schema(description = "Quantidade disponivel em estoque.", example = "25", minimum = "0")
    @NotNull(message = "Stock quantity is required")
    @PositiveOrZero(message = "Stock cannot be negative")
    private Integer stockQuantity;

}
