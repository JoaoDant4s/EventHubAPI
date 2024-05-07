package imd.eventhub.restAPI.dto.participant;

import imd.eventhub.model.CreditCard;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UpdateParticipantDTO {

    private Integer id;
    private CreditCard creditCard;
    private Integer userId;

    public UpdateParticipantDTO(){}
}
