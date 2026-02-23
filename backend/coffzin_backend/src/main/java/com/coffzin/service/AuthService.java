package com.coffzin.service;

import org.springframework.stereotype.Service;

import com.coffzin.dto.request.LoginRequestDTO;
import com.coffzin.model.User;
import com.coffzin.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    public String login(LoginRequestDTO request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new RuntimeException("Email not found"));

        if (!user.getPassword().equals(request.password())) {
            throw new RuntimeException("Incorrect password"); 
        }

        return "Welcome, " + user.getName();
    }
}