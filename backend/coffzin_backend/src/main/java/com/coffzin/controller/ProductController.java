package com.coffzin.controller;

import com.coffzin.config.SwaggerConfig;
import com.coffzin.dto.request.ProductRequestDTO;
import com.coffzin.dto.response.ErrorResponseDTO;
import com.coffzin.dto.response.ProductResponseDTO;
import com.coffzin.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "Products", description = "Cadastro, consulta e manutencao dos produtos vendidos pelo Coffzin.")
public class ProductController {

    private final ProductService productService;

    @Operation(
            summary = "Criar produto",
            description = "Cria um produto com nome, descricao, preco e quantidade em estoque.",
            security = @SecurityRequirement(name = SwaggerConfig.COOKIE_AUTH)
    )
    @ApiResponse(responseCode = "201", description = "Produto criado",
            content = @Content(schema = @Schema(implementation = ProductResponseDTO.class)))
    @ApiResponse(responseCode = "400", description = "Dados invalidos",
            content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    @ApiResponse(responseCode = "401", description = "Login necessario",
            content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductResponseDTO> createProduct(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados do produto.",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = ProductRequestDTO.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "name": "Vanilla Latte",
                                      "description": "Cafe espresso com leite vaporizado e baunilha.",
                                      "price": 18.9,
                                      "stockQuantity": 25
                                    }
                                    """)
                    )
            )
            @Valid @RequestBody ProductRequestDTO request) {

        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(request));
    }

    @Operation(
            summary = "Listar produtos",
            description = "Lista todos os produtos cadastrados. Endpoint publico usado pela vitrine do frontend."
    )
    @ApiResponse(responseCode = "200", description = "Produtos encontrados",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ProductResponseDTO.class))))
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProductResponseDTO>> listAll() {

        return ResponseEntity.ok(productService.listProducts());
    }

    @Operation(
            summary = "Buscar produto por ID",
            description = "Retorna um produto especifico pelo ID. Endpoint publico."
    )
    @ApiResponse(responseCode = "200", description = "Produto encontrado",
            content = @Content(schema = @Schema(implementation = ProductResponseDTO.class)))
    @ApiResponse(responseCode = "404", description = "Produto nao encontrado",
            content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductResponseDTO> getProductById(
            @Parameter(description = "ID do produto.", example = "1", required = true)
            @PathVariable Long id) {

        return ResponseEntity.ok(productService.getProductById(id));
    }

    @Operation(
            summary = "Buscar produtos por nome",
            description = "Pesquisa produtos pelo nome exato cadastrado. Endpoint publico."
    )
    @ApiResponse(responseCode = "200", description = "Produtos encontrados",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ProductResponseDTO.class))))
    @GetMapping(value = "/search/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProductResponseDTO>> searchByName(
            @Parameter(description = "Nome exato do produto.", example = "Vanilla Latte", required = true)
            @PathVariable String name) {

        return ResponseEntity.ok(productService.searchProductByName(name));
    }

    @Operation(
            summary = "Atualizar produto",
            description = "Atualiza nome, descricao, preco e estoque de um produto existente.",
            security = @SecurityRequirement(name = SwaggerConfig.COOKIE_AUTH)
    )
    @ApiResponse(responseCode = "200", description = "Produto atualizado",
            content = @Content(schema = @Schema(implementation = ProductResponseDTO.class)))
    @ApiResponse(responseCode = "400", description = "Dados invalidos",
            content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    @ApiResponse(responseCode = "401", description = "Login necessario",
            content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    @ApiResponse(responseCode = "404", description = "Produto nao encontrado",
            content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductResponseDTO> updateProduct(
            @Parameter(description = "ID do produto que sera atualizado.", example = "1", required = true)
            @PathVariable Long id,
            @Valid @RequestBody ProductRequestDTO request) {

        return ResponseEntity.ok(productService.updateProduct(id, request));
    }

    @Operation(
            summary = "Remover produto",
            description = "Remove permanentemente um produto pelo ID.",
            security = @SecurityRequirement(name = SwaggerConfig.COOKIE_AUTH)
    )
    @ApiResponse(responseCode = "204", description = "Produto removido", content = @Content)
    @ApiResponse(responseCode = "401", description = "Login necessario",
            content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    @ApiResponse(responseCode = "404", description = "Produto nao encontrado",
            content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(
            @Parameter(description = "ID do produto que sera removido.", example = "1", required = true)
            @PathVariable Long id) {

        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
