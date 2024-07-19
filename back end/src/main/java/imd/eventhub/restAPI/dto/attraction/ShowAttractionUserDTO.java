package imd.eventhub.restAPI.dto.attraction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class ShowAttractionUserDTO {

    private Integer id;
    private String email;
    private String name;
    private String cpf;
    private LocalDate birthDate;
    private SaveAttractionDTO attraction;

    public ShowAttractionUserDTO(){};
}
