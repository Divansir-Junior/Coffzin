package com.coffzin.dto.response;

import java.time.LocalDate;

import org.springframework.cglib.core.Local;

import com.coffzin.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {

    private Long id;
    private String name;
    private String email;
    private String cpf;
    private String phoneNumber;
    private LocalDate   birthDate;
    

    public static UserResponseDTO fromEntity(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getCpf(),
                user.getPhoneNumber(),
                user.getBirthDate()
        );
    }
}
