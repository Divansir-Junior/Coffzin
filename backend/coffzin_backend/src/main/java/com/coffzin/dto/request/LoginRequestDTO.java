package com.coffzin.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Credenciais para login.")
public record LoginRequestDTO(
        @Schema(description = "Email cadastrado.", example = "ana.silva@example.com")
        @NotBlank(message = "Email is required")
        @Email(message = "Email must be valid")
        String email,

        @Schema(description = "Senha cadastrada.", example = "abc12345")
        @NotBlank(message = "Password is required")
        String password
) {
}
