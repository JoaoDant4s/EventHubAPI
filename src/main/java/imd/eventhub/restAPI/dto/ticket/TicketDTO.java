package imd.eventhub.restAPI.dto.ticket;

import imd.eventhub.model.Payment;
import imd.eventhub.restAPI.dto.ticketType.TicketTypeDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TicketDTO {
    private Integer id;
    private TicketTypeDTO ticketTypeDTO;
    private String paymentStatus;
    private Payment payment;
}
