package com.imd.web2.user.resources.dto;

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
    private Integer participantId;

    public SaveCreditCardDTO(){}
}
