package imd.eventhub.restAPI.dto.creditCard;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class SaveCreditCardDTO {
    private String cardNumber;
    private LocalDate expiration;
    private String cardHolderName;

    public SaveCreditCardDTO(){}
}
