package com.coffzin.controller;

import com.coffzin.dto.request.LoginRequestDTO;
import com.coffzin.dto.response.AuthResponseDTO;
import com.coffzin.dto.response.ErrorResponseDTO;
import com.coffzin.dto.response.LoginResultDTO;
import com.coffzin.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDTO request) {
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

    @PostMapping("/logout")
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
