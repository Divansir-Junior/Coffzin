package com.coffzin.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {

    private String name;
    private String lastName;
    private String cpf;
    private LocalDate birthDate;
    private String phoneNumber;
    private String email;
    private String password;
    
}
