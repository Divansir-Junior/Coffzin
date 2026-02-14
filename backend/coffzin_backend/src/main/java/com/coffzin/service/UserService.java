package com.coffzin.service;

import com.coffzin.dto.response.UserResponseDTO;
import com.coffzin.model.User;
import com.coffzin.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    
    private final UserRepository userRepository;

    public List<UserResponseDTO> list() {
        List<User> users = userRepository.findAll();

        return users.stream()
                .map(user -> new UserResponseDTO(
                        user.getId(),
                        user.getName(),
                        user.getEmail()
                ))
                .collect(Collectors.toList());
    }
}
