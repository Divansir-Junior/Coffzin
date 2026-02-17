package com.coffzin.dto.request;

import java.time.LocalDate;

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

    private String name;
    private String lastName;
    private String cpf;
    private LocalDate birthDate;
    private String phoneNumber;
    private String email;
    private String password;

    

    
}

