package com.coffzin.controller;

import com.coffzin.config.SwaggerConfig;
import com.coffzin.dto.request.UserRequestDTO;
import com.coffzin.dto.response.ErrorResponseDTO;
import com.coffzin.dto.response.UserResponseDTO;
import com.coffzin.service.UserService;
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
import org.springframework.security.core.Authentication;
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
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "Users", description = "Cadastro, consulta e manutencao dos usuarios do Coffzin.")
public class UserController {

    private final UserService userService;

    @Operation(
            summary = "Criar usuario",
            description = "Cria uma nova conta de usuario. A senha e armazenada com BCrypt e nunca e devolvida na resposta."
    )
    @ApiResponse(responseCode = "201", description = "Usuario criado com sucesso",
            content = @Content(schema = @Schema(implementation = UserResponseDTO.class)))
    @ApiResponse(responseCode = "400", description = "Dados invalidos",
            content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    @ApiResponse(responseCode = "409", description = "Email, CPF ou telefone ja cadastrado",
            content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponseDTO> createUser(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados obrigatorios para criar a conta.",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = UserRequestDTO.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "name": "Ana",
                                      "lastName": "Silva",
                                      "cpf": "123.456.789-01",
                                      "birthDate": "1995-06-15",
                                      "phoneNumber": "(11) 98765-4321",
                                      "email": "ana.silva@example.com",
                                      "password": "abc12345"
                                    }
                                    """)
                    )
            )
            @Valid @RequestBody UserRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(request));
    }

    @Operation(
            summary = "Listar usuarios",
            description = "Lista todos os usuarios cadastrados no banco atual. Use este endpoint no Swagger apos executar login.",
            security = @SecurityRequirement(name = SwaggerConfig.COOKIE_AUTH)
    )
    @ApiResponse(responseCode = "200", description = "Usuarios encontrados",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = UserResponseDTO.class))))
    @ApiResponse(responseCode = "401", description = "Login necessario",
            content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.list());
    }

    @Operation(
            summary = "Usuario autenticado",
            description = "Retorna os dados publicos do usuario autenticado pelo cookie JWT.",
            security = @SecurityRequirement(name = SwaggerConfig.COOKIE_AUTH)
    )
    @ApiResponse(responseCode = "200", description = "Usuario autenticado",
            content = @Content(schema = @Schema(implementation = UserResponseDTO.class)))
    @ApiResponse(responseCode = "401", description = "Login necessario",
            content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    @GetMapping(value = "/me", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponseDTO> getCurrentUser(Authentication authentication) {
        return ResponseEntity.ok(userService.getByEmail(authentication.getName()));
    }

    @Operation(
            summary = "Buscar usuario por ID",
            description = "Busca um usuario especifico pelo identificador interno.",
            security = @SecurityRequirement(name = SwaggerConfig.COOKIE_AUTH)
    )
    @ApiResponse(responseCode = "200", description = "Usuario encontrado",
            content = @Content(schema = @Schema(implementation = UserResponseDTO.class)))
    @ApiResponse(responseCode = "401", description = "Login necessario",
            content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    @ApiResponse(responseCode = "404", description = "Usuario nao encontrado",
            content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponseDTO> getUserById(
            @Parameter(description = "ID do usuario.", example = "1", required = true)
            @PathVariable Long id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @Operation(
            summary = "Buscar usuario por email",
            description = "Busca um usuario pelo email normalizado em minusculas.",
            security = @SecurityRequirement(name = SwaggerConfig.COOKIE_AUTH)
    )
    @ApiResponse(responseCode = "200", description = "Usuario encontrado",
            content = @Content(schema = @Schema(implementation = UserResponseDTO.class)))
    @ApiResponse(responseCode = "401", description = "Login necessario",
            content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    @ApiResponse(responseCode = "404", description = "Usuario nao encontrado",
            content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    @GetMapping(value = "/email/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponseDTO> getUserByEmail(
            @Parameter(description = "Email do usuario.", example = "ana.silva@example.com", required = true)
            @PathVariable String email) {
        return ResponseEntity.ok(userService.getByEmail(email));
    }

    @Operation(
            summary = "Atualizar usuario",
            description = "Atualiza nome, sobrenome, telefone e email do usuario informado. CPF, data de nascimento e senha nao sao alterados por este endpoint.",
            security = @SecurityRequirement(name = SwaggerConfig.COOKIE_AUTH)
    )
    @ApiResponse(responseCode = "200", description = "Usuario atualizado",
            content = @Content(schema = @Schema(implementation = UserResponseDTO.class)))
    @ApiResponse(responseCode = "400", description = "Dados invalidos",
            content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    @ApiResponse(responseCode = "401", description = "Login necessario",
            content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    @ApiResponse(responseCode = "404", description = "Usuario nao encontrado",
            content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    @ApiResponse(responseCode = "409", description = "Email ou telefone ja cadastrado",
            content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponseDTO> updateUser(
            @Parameter(description = "ID do usuario que sera atualizado.", example = "1", required = true)
            @PathVariable Long id,
            @Valid @RequestBody UserRequestDTO request) {
        return ResponseEntity.ok(userService.updateUser(id, request));
    }

    @Operation(
            summary = "Remover usuario",
            description = "Remove permanentemente um usuario pelo ID.",
            security = @SecurityRequirement(name = SwaggerConfig.COOKIE_AUTH)
    )
    @ApiResponse(responseCode = "204", description = "Usuario removido", content = @Content)
    @ApiResponse(responseCode = "401", description = "Login necessario",
            content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    @ApiResponse(responseCode = "404", description = "Usuario nao encontrado",
            content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @Parameter(description = "ID do usuario que sera removido.", example = "1", required = true)
            @PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
