package com.coffzin.dto.response;

import java.time.Instant;
import java.util.Map;

public record ErrorResponseDTO(
        Instant timestamp,
        int status,
        String message,
        Map<String, String> errors
) {
    public static ErrorResponseDTO of(int status, String message) {
        return new ErrorResponseDTO(Instant.now(), status, message, Map.of());
    }

    public static ErrorResponseDTO of(int status, String message, Map<String, String> errors) {
        return new ErrorResponseDTO(Instant.now(), status, message, errors);
    }
}
