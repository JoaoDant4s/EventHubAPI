package imd.eventhub.restAPI.dto.attraction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

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
