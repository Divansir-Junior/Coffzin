package com.coffzin.service;

import com.coffzin.dto.request.LoginRequestDTO;
import com.coffzin.dto.response.LoginResultDTO;
import com.coffzin.dto.response.UserResponseDTO;
import com.coffzin.model.User;
import com.coffzin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final BCryptPasswordEncoder passwordEncoder;

    public LoginResultDTO login(LoginRequestDTO request) {
        String email = request.email().trim().toLowerCase();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BadCredentialsException("Invalid credentials"));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new BadCredentialsException("Invalid credentials");
        }

        return new LoginResultDTO(
                jwtService.generateToken(user.getEmail()),
                UserResponseDTO.fromEntity(user)
        );
    }
}
