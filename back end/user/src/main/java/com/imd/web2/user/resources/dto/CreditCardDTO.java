package com.imd.web2.user.resources.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import com.imd.web2.user.model.CreditCard;

import java.time.LocalDate;

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

    public static CreditCardDTO toCreditCardDTO(CreditCard creditCard){
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
