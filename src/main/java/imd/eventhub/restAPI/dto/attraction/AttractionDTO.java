package imd.eventhub.restAPI.dto.attraction;

import imd.eventhub.model.Attraction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AttractionDTO {
    private Integer id;
    private String description;
    private String contact;
    private Integer userId;
    public AttractionDTO(){}

    public static AttractionDTO convertAttractionToAttractionDTO(Attraction attraaction){
        AttractionDTO attractionDTO = new AttractionDTO();
        attractionDTO.setId(attraaction.getId());
        attractionDTO.setDescription(attraaction.getDescription());
        attractionDTO.setContact(attraaction.getContact());
        attractionDTO.setUserId(attraaction.getUser().getId());
        return attractionDTO;
    }
}

