package imd.eventhub.restAPI.dto.creditCard;

import imd.eventhub.model.Participant;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class CreditCardDTO {
    private Integer id;
    private String cardNumber;
    private LocalDate expiration;
    private String cardHolderName;
    private Integer participantId;

    public CreditCardDTO(){}
}
