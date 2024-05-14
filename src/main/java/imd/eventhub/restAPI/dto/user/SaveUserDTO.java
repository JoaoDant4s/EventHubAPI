package imd.eventhub.restAPI.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
public class SaveUserDTO {
    private String name;
    private String cpf;
    private LocalDate birthDate;
    private String email;
    private String password;

    public SaveUserDTO(){}

    public SaveUserDTO(String name, String cpf) {
        this.name = name;
        this.cpf = cpf;
    }

    public SaveUserDTO(String name, String cpf, String birthDate, String email, String password) {
        this.name = name;
        this.cpf = cpf;
        this.birthDate = LocalDate.parse(birthDate);
        this.email = email;
        this.password = password;
    }
}
