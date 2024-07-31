package main.java.com.imd.web2.user.resources.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class UpdateUserDTO {
    private Integer id;
    private String name;
    private String cpf;
    private LocalDate birthDate;

    public UpdateUserDTO(){}

    public UpdateUserDTO(String name, String cpf) {
        this.name = name;
        this.cpf = cpf;
    }

    public UpdateUserDTO(String name, String cpf, String birthDate) {
        this.name = name;
        this.cpf = cpf;
        this.birthDate = LocalDate.parse(birthDate);
    }
}
