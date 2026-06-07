package com.coffzin.service;

import com.coffzin.dto.request.UserRequestDTO;
import com.coffzin.dto.response.UserResponseDTO;
import com.coffzin.exception.DuplicateResourceException;
import com.coffzin.exception.ResourceNotFoundException;
import com.coffzin.model.User;
import com.coffzin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserResponseDTO create(UserRequestDTO request) {
        String cpf = onlyDigits(request.getCpf());
        String phoneNumber = onlyDigitsOrNull(request.getPhoneNumber());
        String email = normalizeEmail(request.getEmail());

        validateCpf(cpf);
        validatePhoneNumber(phoneNumber);

        if (userRepository.existsByCpf(cpf)) {
            throw new DuplicateResourceException("CPF already registered");
        }

        if (userRepository.existsByEmail(email)) {
            throw new DuplicateResourceException("Email already registered");
        }

        if (phoneNumber != null && userRepository.existsByPhoneNumber(phoneNumber)) {
            throw new DuplicateResourceException("Phone number already registered");
        }

        User user = new User();
        user.setName(request.getName().trim());
        user.setLastName(request.getLastName().trim());
        user.setCpf(cpf);
        user.setBirthDate(request.getBirthDate());
        user.setPhoneNumber(phoneNumber);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        return UserResponseDTO.fromEntity(userRepository.save(user));
    }

    public List<UserResponseDTO> list() {
        return userRepository.findAll()
                .stream()
                .map(UserResponseDTO::fromEntity)
                .toList();
    }

    public UserResponseDTO getById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("User id cannot be null");
        }

        return userRepository.findById(id)
                .map(UserResponseDTO::fromEntity)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public UserResponseDTO getByEmail(String email) {
        return userRepository.findByEmail(normalizeEmail(email))
                .map(UserResponseDTO::fromEntity)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public UserResponseDTO updateUser(Long id, UserRequestDTO request) {
        if (id == null) {
            throw new IllegalArgumentException("User id cannot be null");
        }

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        String email = normalizeEmail(request.getEmail());
        String phoneNumber = onlyDigitsOrNull(request.getPhoneNumber());

        validatePhoneNumber(phoneNumber);

        if (userRepository.existsByEmailAndIdNot(email, id)) {
            throw new DuplicateResourceException("Email already registered");
        }

        if (phoneNumber != null && userRepository.existsByPhoneNumberAndIdNot(phoneNumber, id)) {
            throw new DuplicateResourceException("Phone number already registered");
        }

        user.setName(request.getName().trim());
        user.setLastName(request.getLastName().trim());
        user.setPhoneNumber(phoneNumber);
        user.setEmail(email);

        return UserResponseDTO.fromEntity(userRepository.save(user));
    }

    public void deleteById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("User id cannot be null");
        }

        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found");
        }

        userRepository.deleteById(id);
    }

    private String normalizeEmail(String email) {
        if (email == null) {
            throw new IllegalArgumentException("Email is required");
        }

        return email.trim().toLowerCase();
    }

    private String onlyDigits(String value) {
        if (value == null) {
            return null;
        }

        return value.replaceAll("\\D", "");
    }

    private String onlyDigitsOrNull(String value) {
        String digits = onlyDigits(value);
        return digits == null || digits.isBlank() ? null : digits;
    }

    private void validateCpf(String cpf) {
        if (cpf == null || cpf.length() != 11) {
            throw new IllegalArgumentException("CPF must contain 11 digits");
        }
    }

    private void validatePhoneNumber(String phoneNumber) {
        if (phoneNumber != null && (phoneNumber.length() < 10 || phoneNumber.length() > 11)) {
            throw new IllegalArgumentException("Phone number must contain 10 or 11 digits");
        }
    }
}
