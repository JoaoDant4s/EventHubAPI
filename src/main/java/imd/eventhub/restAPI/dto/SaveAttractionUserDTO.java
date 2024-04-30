package imd.eventhub.restAPI.dto;

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
    private String birthDate;
    private String description;
    private String contact;
}
