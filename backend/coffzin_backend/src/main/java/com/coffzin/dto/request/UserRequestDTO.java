package com.coffzin.dto.request;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Payload usado para criar ou atualizar usuario.")
public class UserRequestDTO {

    @Schema(description = "Primeiro nome do usuario.", example = "Ana", maxLength = 80)
    @NotBlank(message = "Name is required")
    @Size(max = 80, message = "Name must have at most 80 characters")
    private String name;

    @Schema(description = "Sobrenome do usuario.", example = "Silva", maxLength = 120)
    @NotBlank(message = "Last name is required")
    @Size(max = 120, message = "Last name must have at most 120 characters")
    private String lastName;

    @Schema(description = "CPF com ou sem mascara. Sera salvo apenas com digitos.", example = "123.456.789-01")
    @NotBlank(message = "CPF is required")
    @Pattern(regexp = "^[0-9.\\-]{11,14}$", message = "CPF must contain 11 digits")
    private String cpf;

    @Schema(description = "Data de nascimento no formato ISO yyyy-MM-dd.", example = "1995-06-15", type = "string", format = "date")
    @NotNull(message = "Birth date is required")
    @Past(message = "Birth date must be in the past")
    private LocalDate birthDate;

    @Schema(description = "Telefone com ou sem mascara. Opcional.", example = "(11) 98765-4321")
    @Pattern(regexp = "^$|^[0-9()\\s+\\-]{10,16}$", message = "Phone number must contain 10 or 11 digits")
    private String phoneNumber;

    @Schema(description = "Email usado para login. Sera salvo em minusculas.", example = "ana.silva@example.com", maxLength = 160)
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    @Size(max = 160, message = "Email must have at most 160 characters")
    private String email;

    @Schema(description = "Senha com 8 a 72 caracteres, contendo letras e numeros.", example = "abc12345", minLength = 8, maxLength = 72)
    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 72, message = "Password must have between 8 and 72 characters")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d).+$", message = "Password must contain letters and numbers")
    private String password;
}

