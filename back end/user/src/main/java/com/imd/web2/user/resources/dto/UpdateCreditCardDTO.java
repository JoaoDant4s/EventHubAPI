package com.imd.web2.user.resources.dto;

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

    public UpdateCreditCardDTO(){}
}
