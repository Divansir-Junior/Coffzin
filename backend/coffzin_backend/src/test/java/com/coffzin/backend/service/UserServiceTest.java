package com.coffzin.backend.service; // Altere para o pacote real do seu teste (ex: com.coffzin.service)

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDate;

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
}