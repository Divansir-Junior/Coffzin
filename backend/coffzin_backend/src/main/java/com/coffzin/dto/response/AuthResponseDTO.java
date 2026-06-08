package com.coffzin.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Resposta dos endpoints de autenticacao.")
public record AuthResponseDTO(
        @Schema(description = "Mensagem de resultado.", example = "Login successful")
        String message,

        @Schema(description = "Usuario autenticado. Nulo no logout.")
        UserResponseDTO user
) {
}
