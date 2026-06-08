package com.coffzin.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;
import java.util.Map;

@Schema(description = "Resposta padrao de erro da API.")
public record ErrorResponseDTO(
        @Schema(description = "Momento em que o erro ocorreu.", example = "2026-06-07T22:00:00Z")
        Instant timestamp,

        @Schema(description = "Status HTTP numerico.", example = "400")
        int status,

        @Schema(description = "Mensagem resumida do erro.", example = "Invalid request data")
        String message,

        @Schema(description = "Erros por campo, quando houver validacao de payload.")
        Map<String, String> errors
) {
    public static ErrorResponseDTO of(int status, String message) {
        return new ErrorResponseDTO(Instant.now(), status, message, Map.of());
    }

    public static ErrorResponseDTO of(int status, String message, Map<String, String> errors) {
        return new ErrorResponseDTO(Instant.now(), status, message, errors);
    }
}
