package com.amigopay.user.user.dto;

import com.amigopay.user.common.validation.annotation.ValidName;
import com.amigopay.user.common.validation.annotation.ValidPassword;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserRequest {

    @NotBlank(message = "{validation.name.required}")
    @Size(min = 2, max = 30, message = "{validation.name.invalid}")
    @ValidName
    private String firstName;

    @NotBlank(message = "{validation.lastname.required}")
    @Size(min = 2, max = 50, message = "{validation.lastname.invalid}")
    @ValidName
    private String lastName;

    @NotNull(message = "{validation.birthdate.required}")
    @Past(message = "{validation.birthdate.invalid}")
    private LocalDate birthDate;

    @NotBlank(message = "{validation.cpf.required}")
    @CPF(message = "{validation.cpf.invalid}")
    private String cpf;

    @NotBlank(message = "{validation.email.required}")
    @Email(message = "{validation.email.invalid_format}")
    @Size(max = 150, message = "{validation.email.max_length}")
    private String email;

    @NotBlank(message = "{validation.password.required}")
    @ValidPassword
    private String password;
}
