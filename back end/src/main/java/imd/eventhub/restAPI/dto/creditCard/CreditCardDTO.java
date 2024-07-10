package imd.eventhub.restAPI.dto.creditCard;

import imd.eventhub.model.CreditCard;
import imd.eventhub.model.Participant;
import imd.eventhub.model.User;
import imd.eventhub.restAPI.dto.user.UserDTO;
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

    public static CreditCardDTO convertCreditCardToCreditCardDTO(CreditCard creditCard){
        CreditCardDTO creditCardDTO = new CreditCardDTO();
        creditCardDTO.setId(creditCard.getId());
        creditCardDTO.setCardNumber(creditCard.getCardNumber());
        creditCardDTO.setCardHolderName(creditCard.getCardHolderName());
        creditCardDTO.setExpiration(creditCard.getExpiration());

        if (creditCard.getParticipant() != null) {
            creditCardDTO.setParticipantId(creditCard.getParticipant().getId());
        } else {
            creditCardDTO.setParticipantId(null);
        }

        return creditCardDTO;
    }
}
