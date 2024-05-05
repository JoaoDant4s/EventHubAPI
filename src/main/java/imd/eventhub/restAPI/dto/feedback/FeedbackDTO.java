package imd.eventhub.restAPI.dto.feedback;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class FeedbackDTO {
    private Integer id;
    private String comment;
    private Integer rating;
    private LocalDateTime dateTime;
    private Integer userId;
    private Integer eventId;

    public FeedbackDTO(){}
}
