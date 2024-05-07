package imd.eventhub.restAPI.dto.creditCard;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class UpdateCreditCardDTO {
    private Integer id;
    private String cardNumber;
    private LocalDate expiration;
    private String cardHolderName;
    private Integer participantId;

    public UpdateCreditCardDTO(){}
}
