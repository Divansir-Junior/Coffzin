package com.coffzin.controller;

import org.apache.coyote.Response;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.coffzin.dto.request.LoginRequestDTO;
import com.coffzin.service.AuthService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDTO request, HttpServletResponse response) {
        try {
            String token = authService.login(request);

            response.setHeader("Set-Cookie",
                "token=" + token + "; Path=/; HttpOnly; Max-Age=86400; SameSite=None");

            return ResponseEntity.ok("Login realizado com sucesso");
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

   @PostMapping("/logout")
public ResponseEntity<String> logout(HttpServletResponse response) {
    ResponseCookie cookie = ResponseCookie.from("token", "")
            .httpOnly(true)
            .secure(true)
            .path("/")
            .maxAge(0)
            .sameSite("Strict")
            .build();

    return ResponseEntity.ok()
            .header(HttpHeaders.SET_COOKIE, cookie.toString())
            .body("Logout realizado com sucesso");
}
}