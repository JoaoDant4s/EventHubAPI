package imd.eventhub.restAPI.dto.attraction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class SaveAttractionUserDTO {
    private String name;
    private String cpf;
    private LocalDate birthDate;
    private String email;
    private String password;
    private SaveAttractionDTO attraction;

    public SaveAttractionUserDTO(){};
}
