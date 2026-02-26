package com.coffzin.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.coffzin.dto.request.LoginRequestDTO;
import com.coffzin.model.User;
import com.coffzin.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final BCryptPasswordEncoder passwordEncoder;

    public String login(LoginRequestDTO request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new RuntimeException("Email not found"));

        // Compara a senha com o hash salvo no banco
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new RuntimeException("Incorrect password");
        }

        return jwtService.generateToken(user.getEmail());
    }
}