package imd.eventhub.restAPI.dto.attraction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class UpdateAttractionInfoDTO {
    private Integer id;
    private String name;
    private String cpf;
    private LocalDate birthDate;
    private String description;
    private String contact;

    public UpdateAttractionInfoDTO(){}
}

