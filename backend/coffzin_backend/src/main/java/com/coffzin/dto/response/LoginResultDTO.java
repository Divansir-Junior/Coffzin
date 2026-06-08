package com.coffzin.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Resultado interno do login. O token nao e exposto em respostas REST.")
public record LoginResultDTO(
        @Schema(description = "JWT gerado internamente.")
        String token,

        @Schema(description = "Usuario autenticado.")
        UserResponseDTO user
) {
}
