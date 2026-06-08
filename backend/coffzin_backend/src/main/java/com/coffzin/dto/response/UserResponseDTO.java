package com.coffzin.dto.response;

import com.coffzin.model.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados publicos de usuario. A senha nunca e retornada.")
public class UserResponseDTO {

    @Schema(description = "ID interno do usuario.", example = "1")
    private Long id;

    @Schema(description = "Primeiro nome.", example = "Ana")
    private String name;

    @Schema(description = "Email normalizado.", example = "ana.silva@example.com")
    private String email;

    @Schema(description = "CPF salvo apenas com digitos.", example = "12345678901")
    private String cpf;

    @Schema(description = "Telefone salvo apenas com digitos.", example = "11987654321")
    private String phoneNumber;

    @Schema(description = "Data de nascimento.", example = "1995-06-15", type = "string", format = "date")
    private LocalDate birthDate;

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
