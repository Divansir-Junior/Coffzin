package com.coffzin.service;

import com.coffzin.dto.request.UserRequestDTO;
import com.coffzin.dto.response.UserResponseDTO;
import com.coffzin.model.User;
import com.coffzin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponseDTO create(UserRequestDTO request) {

        User user = new User();
        user.setName(request.getName());
        user.setLastName(request.getLastName());
        user.setCpf(request.getCpf());
        user.setBirthDate(request.getBirthDate());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        return UserResponseDTO.fromEntity(userRepository.save(user));
    }

    public List<UserResponseDTO> list() {
        return userRepository.findAll()
                .stream()
                .map(UserResponseDTO::fromEntity)
                .toList();
    }

    public UserResponseDTO getById(Long id) {
        return userRepository.findById(id)
                .map(UserResponseDTO::fromEntity)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    public UserResponseDTO getByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(UserResponseDTO::fromEntity)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }

    public UserResponseDTO updateUser(Long id, UserRequestDTO request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setName(request.getName());
        user.setLastName(request.getLastName());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setEmail(request.getEmail());

        return UserResponseDTO.fromEntity(userRepository.save(user));
    }

    public void deleteById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(id);
    }
}
