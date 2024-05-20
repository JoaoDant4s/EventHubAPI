package imd.eventhub.restAPI.dto.payment;

import imd.eventhub.model.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDTO {
    private UUID id;
    private LocalDateTime date;
    private BigDecimal totalAmount;
    private PaymentType paymentType;
}
