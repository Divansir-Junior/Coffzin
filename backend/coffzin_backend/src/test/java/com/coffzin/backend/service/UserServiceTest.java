package com.coffzin.backend.service; // Altere para o pacote real do seu teste (ex: com.coffzin.service)

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.coffzin.dto.request.UserRequestDTO;
import com.coffzin.dto.response.UserResponseDTO;
import com.coffzin.model.User;
import com.coffzin.repository.UserRepository;
import com.coffzin.service.UserService;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void createUser() {
        
        UserRequestDTO request = UserRequestDTO.builder()
        .name("Test User")
        .lastName("Sobrenome")
        .cpf("212.231.212")
        .birthDate(LocalDate.of(1990, 5, 15))
        .phoneNumber("11987654321")
        .email("testuser@example.com")
        .password("bc3")
        .build();


        // Cria a entidade que será retornada pelo mock do repositório
        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setName("Test User");
        savedUser.setLastName("Sobrenome");
        savedUser.setEmail("testuser@example.com");
        savedUser.setPassword("bc3");
        savedUser.setBirthDate(LocalDate.of(1990, 5, 15));
        savedUser.setCpf("212.231.212");
        savedUser.setPhoneNumber("11987654321");

        // Configura o mock para retornar o usuário salvo
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // When: chama o método real do service
        UserResponseDTO result = userService.create(request);

        // Then: verifica o resultado e as interações
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test User", result.getName());
        assertEquals("testuser@example.com", result.getEmail());
        assertEquals(LocalDate.of(1990, 5, 15), result.getBirthDate());
        assertEquals("212.231.212", result.getCpf());
        assertEquals("11987654321", result.getPhoneNumber());

        // Garante que o método save foi chamado exatamente uma vez
        verify(userRepository, times(1)).save(any(User.class));
    }

   @Test
    void getUserById() {

    // Arrange
    Long userId = 1L;

    User user = new User();
    user.setId(userId);
    user.setName("Test User");
    user.setLastName("Sobrenome");
    user.setCpf("212.231.212");
    user.setBirthDate(LocalDate.of(1990, 5, 15));
    user.setPhoneNumber("11987654321");
    user.setEmail("testuser@example.com");
    user.setPassword("bc3");

    when(userRepository.findById(userId)).thenReturn(Optional.of(user));

    // Act
    UserResponseDTO response = userService.getById(userId);

    // Assert
    assertNotNull(response);
    assertEquals(userId, response.getId());
    assertEquals("Test User", response.getName());
    assertEquals("testuser@example.com", response.getEmail());

    verify(userRepository, times(1)).findById(userId);
}

    @Test
    void updateUser() {

    // Arrange
    Long userId = 1L;

    UserRequestDTO request = UserRequestDTO.builder()
            .name("Updated User")
            .lastName("Sobrenome")
            .cpf("212.231.212")
            .birthDate(LocalDate.of(1990, 5, 15))
            .phoneNumber("11987654321")
            .email("updated@example.com")
            .password("bc3")
            .build();

    User existingUser = new User();
    existingUser.setId(userId);
    existingUser.setName("Test User");
    existingUser.setLastName("Sobrenome");
    existingUser.setCpf("212.231.212");
    existingUser.setBirthDate(LocalDate.of(1990, 5, 15));
    existingUser.setPhoneNumber("11987654321");
    existingUser.setEmail("old@example.com");
    existingUser.setPassword("bc3");

    User updatedUser = new User();
    updatedUser.setId(userId);
    updatedUser.setName("Updated User");
    updatedUser.setLastName("Sobrenome");
    updatedUser.setCpf("212.231.212");
    updatedUser.setBirthDate(LocalDate.of(1990, 5, 15));
    updatedUser.setPhoneNumber("11987654321");
    updatedUser.setEmail("updated@example.com");
    updatedUser.setPassword("bc3");

    when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
    when(userRepository.save(any(User.class))).thenReturn(updatedUser);

    // Act
    UserResponseDTO response = userService.updateUser(userId, request);

    // Assert
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

        when(userRepository.existsById(userId)).thenReturn(true, false);
        userService.deleteById(userId);

        verify(userRepository, times(1)).existsById(userId);
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void searchByEmail() {
        String email = "teste@gmail.com";

        User user = new User();
        user.setId(1L);
        user.setName("Test User");
        user.setLastName("Sobrenome");
        user.setCpf("212.231.212");
        user.setBirthDate(LocalDate.of(1990, 5, 15));
        user.setPhoneNumber("11987654321");
        user.setEmail(email);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        UserResponseDTO response = userService.getByEmail(email);
        assertNotNull(response);
        assertEquals(email, response.getEmail());
        verify(userRepository, times(1)).findByEmail(email);
    }


}