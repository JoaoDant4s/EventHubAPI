package imd.eventhub.restAPI.dto.attraction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UpdateAttractionDTO {
    private Integer id;
    private String description;
    private String contact;

    public UpdateAttractionDTO(){}
}

