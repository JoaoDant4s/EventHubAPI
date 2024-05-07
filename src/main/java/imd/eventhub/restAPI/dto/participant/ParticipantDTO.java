package imd.eventhub.restAPI.dto.participant;

import imd.eventhub.model.CreditCard;
import imd.eventhub.model.Ticket;
import imd.eventhub.model.User;
import imd.eventhub.restAPI.dto.ticket.TicketDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ParticipantDTO {

    private Integer id;
    private CreditCard creditCard;
    private Integer userId;

    public ParticipantDTO(){}
}
