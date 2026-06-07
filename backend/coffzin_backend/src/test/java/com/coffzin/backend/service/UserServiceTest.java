package com.coffzin.backend.service;

import com.coffzin.dto.request.UserRequestDTO;
import com.coffzin.dto.response.UserResponseDTO;
import com.coffzin.model.User;
import com.coffzin.repository.UserRepository;
import com.coffzin.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void createUser() {
        UserRequestDTO request = defaultRequest()
                .email("TestUser@Example.com")
                .password("abc12345")
                .build();

        User savedUser = defaultUser();
        savedUser.setEmail("testuser@example.com");
        savedUser.setPassword("encoded-password");

        when(passwordEncoder.encode("abc12345")).thenReturn("encoded-password");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        UserResponseDTO result = userService.create(request);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test User", result.getName());
        assertEquals("testuser@example.com", result.getEmail());
        assertEquals(LocalDate.of(1990, 5, 15), result.getBirthDate());
        assertEquals("12345678901", result.getCpf());
        assertEquals("11987654321", result.getPhoneNumber());

        verify(passwordEncoder, times(1)).encode("abc12345");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void getUserById() {
        Long userId = 1L;
        User user = defaultUser();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        UserResponseDTO response = userService.getById(userId);

        assertNotNull(response);
        assertEquals(userId, response.getId());
        assertEquals("Test User", response.getName());
        assertEquals("testuser@example.com", response.getEmail());

        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void updateUser() {
        Long userId = 1L;
        UserRequestDTO request = defaultRequest()
                .name("Updated User")
                .email("updated@example.com")
                .build();

        User existingUser = defaultUser();
        existingUser.setEmail("old@example.com");

        User updatedUser = defaultUser();
        updatedUser.setName("Updated User");
        updatedUser.setEmail("updated@example.com");

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        UserResponseDTO response = userService.updateUser(userId, request);

        assertNotNull(response);
        assertEquals(userId, response.getId());
        assertEquals("Updated User", response.getName());
        assertEquals("updated@example.com", response.getEmail());

        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void deleteUser() {
        Long userId = 1L;

        when(userRepository.existsById(userId)).thenReturn(true);
        userService.deleteById(userId);

        verify(userRepository, times(1)).existsById(userId);
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void searchByEmail() {
        String email = "teste@gmail.com";
        User user = defaultUser();
        user.setEmail(email);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        UserResponseDTO response = userService.getByEmail(email);

        assertNotNull(response);
        assertEquals(email, response.getEmail());
        verify(userRepository, times(1)).findByEmail(email);
    }

    private UserRequestDTO.UserRequestDTOBuilder defaultRequest() {
        return UserRequestDTO.builder()
                .name("Test User")
                .lastName("Sobrenome")
                .cpf("123.456.789-01")
                .birthDate(LocalDate.of(1990, 5, 15))
                .phoneNumber("(11) 98765-4321")
                .email("testuser@example.com")
                .password("abc12345");
    }

    private User defaultUser() {
        User user = new User();
        user.setId(1L);
        user.setName("Test User");
        user.setLastName("Sobrenome");
        user.setCpf("12345678901");
        user.setBirthDate(LocalDate.of(1990, 5, 15));
        user.setPhoneNumber("11987654321");
        user.setEmail("testuser@example.com");
        user.setPassword("encoded-password");
        return user;
    }
}
