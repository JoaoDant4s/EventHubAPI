package imd.eventhub.restAPI.dto.participant;

import imd.eventhub.model.CreditCard;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class SaveParticipantDTO {
    private String name;
    private String cpf;
    private LocalDate birthDate;
    private String email;
    private String password;

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
