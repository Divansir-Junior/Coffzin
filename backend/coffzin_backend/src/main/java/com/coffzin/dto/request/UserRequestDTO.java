package com.coffzin.dto.request;

import java.time.LocalDate;

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
public class UserRequestDTO {

    @NotBlank(message = "Name is required")
    @Size(max = 80, message = "Name must have at most 80 characters")
    private String name;

    @NotBlank(message = "Last name is required")
    @Size(max = 120, message = "Last name must have at most 120 characters")
    private String lastName;

    @NotBlank(message = "CPF is required")
    @Pattern(regexp = "^[0-9.\\-]{11,14}$", message = "CPF must contain 11 digits")
    private String cpf;

    @NotNull(message = "Birth date is required")
    @Past(message = "Birth date must be in the past")
    private LocalDate birthDate;

    @Pattern(regexp = "^$|^[0-9()\\s+\\-]{10,16}$", message = "Phone number must contain 10 or 11 digits")
    private String phoneNumber;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    @Size(max = 160, message = "Email must have at most 160 characters")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 72, message = "Password must have between 8 and 72 characters")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d).+$", message = "Password must contain letters and numbers")
    private String password;
}

