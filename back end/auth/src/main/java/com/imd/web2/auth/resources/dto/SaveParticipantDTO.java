package com.imd.web2.auth.resources.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class SaveParticipantDTO {
    @NotNull(message = "Email é obrigatório")
    @Size(max = 255)
    private String email;
    @NotNull(message = "Senha é obrigatória")
    @Size(max = 255)
    private String password;
    @NotNull(message = "Confirmação de senha é obrigatória")
    @Size(max = 255)
    private String confirmPassword;
    @NotNull(message = "Nome é obrigatório")
    @Size(max = 100)
    private String name;
    @NotNull(message = "CPF/CNPJ é obrigatório")
    @Size(max = 18)
    private String cpf;
    @NotNull(message = "Data de nascimento é obrigatória")
    private LocalDate birthDate;

    public SaveParticipantDTO(){}

    public SaveParticipantDTO(String name, String cpf) {
        this.name = name;
        this.cpf = cpf;
    }

    public SaveParticipantDTO(String name, String cpf, String birthDate, String email, String password) {
        this.name = name;
        this.cpf = cpf;
        this.birthDate = LocalDate.parse(birthDate);
        this.email = email;
        this.password = password;
    }
}
