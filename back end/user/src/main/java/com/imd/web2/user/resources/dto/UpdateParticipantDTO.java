package com.imd.web2.user.resources.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class UpdateParticipantDTO {

    private Integer id;
    private String email;
    private String password;
    private String confirmPassword;
    private String name;
    private String cpf;
    private LocalDate birthDate;

    public UpdateParticipantDTO(){}
}
