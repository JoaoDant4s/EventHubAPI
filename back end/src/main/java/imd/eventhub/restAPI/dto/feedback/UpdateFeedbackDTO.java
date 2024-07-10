package imd.eventhub.restAPI.dto.feedback;

import imd.eventhub.restAPI.dto.attraction.UpdateAttractionDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UpdateFeedbackDTO {
    private Integer id;
    private String comment;
    private Integer rating;

    public UpdateFeedbackDTO(){}
}
