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
        // ✅ Busca o usuário normalmente
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new RuntimeException("Credenciais inválidas"));
                // ✅ Mensagem genérica — não confirma se o email existe (evita enumeração de usuários)

        // ✅ Compara senha com hash BCrypt
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new RuntimeException("Credenciais inválidas");
            // ✅ Mesma mensagem para email errado e senha errada — comportamento seguro
        }

        return jwtService.generateToken(user.getEmail());
    }
}