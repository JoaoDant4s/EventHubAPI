package imd.eventhub.restAPI.dto.payment;

import imd.eventhub.model.PaymentType;
import imd.eventhub.restAPI.dto.ticket.TicketToPayDTO;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SavePaymentDTO {

    @NotNull
    private TicketToPayDTO ticketToPayDTO;
    @NotNull
    private PaymentType paymentType;
}
