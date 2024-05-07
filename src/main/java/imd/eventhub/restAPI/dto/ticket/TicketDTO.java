package imd.eventhub.restAPI.dto.ticket;

import imd.eventhub.model.Event;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class TicketDTO {
    private Integer id;
    private String description;
    private String batch;
    private float amount;
    private Integer eventId;

    public TicketDTO(){}
}
