package imd.eventhub.restAPI.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SaveAttractionDTO {
    private String description;
    private String contact;
    public SaveAttractionDTO(){}
}

