package com.coffzin.controller;

import com.coffzin.dto.request.LoginRequestDTO;
import com.coffzin.dto.response.AuthResponseDTO;
import com.coffzin.dto.response.ErrorResponseDTO;
import com.coffzin.dto.response.LoginResultDTO;
import com.coffzin.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Login e logout com JWT salvo em cookie HttpOnly.")
public class AuthController {

    private final AuthService authService;

    @Operation(
            summary = "Login",
            description = "Autentica o usuario por email e senha. Em caso de sucesso, grava o JWT no cookie HttpOnly token e retorna os dados publicos do usuario."
    )
    @ApiResponse(responseCode = "200", description = "Login realizado. O header Set-Cookie contem o JWT.",
            content = @Content(schema = @Schema(implementation = AuthResponseDTO.class)))
    @ApiResponse(responseCode = "400", description = "Payload invalido",
            content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    @ApiResponse(responseCode = "401", description = "Email ou senha invalidos",
            content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Credenciais do usuario cadastrado.",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = LoginRequestDTO.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "email": "ana.silva@example.com",
                                      "password": "abc12345"
                                    }
                                    """)
                    )
            )
            @Valid @RequestBody LoginRequestDTO request) {
        try {
            LoginResultDTO result = authService.login(request);

            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, buildTokenCookie(result.token(), Duration.ofDays(1)).toString())
                    .body(new AuthResponseDTO("Login successful", result.user()));
        } catch (BadCredentialsException exception) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ErrorResponseDTO.of(HttpStatus.UNAUTHORIZED.value(), "Invalid email or password"));
        }
    }

    @Operation(
            summary = "Logout",
            description = "Remove o cookie token do navegador, encerrando a sessao JWT atual."
    )
    @ApiResponse(responseCode = "200", description = "Logout realizado",
            content = @Content(schema = @Schema(implementation = AuthResponseDTO.class)))
    @PostMapping(value = "/logout", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthResponseDTO> logout() {
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, buildTokenCookie("", Duration.ZERO).toString())
                .body(new AuthResponseDTO("Logout successful", null));
    }

    private ResponseCookie buildTokenCookie(String token, Duration maxAge) {
        return ResponseCookie.from("token", token)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(maxAge)
                .sameSite("Lax")
                .build();
    }
}
