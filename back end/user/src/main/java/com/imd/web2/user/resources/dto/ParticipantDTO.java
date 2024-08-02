package com.imd.web2.user.resources.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


import com.imd.web2.user.model.CreditCard;

@Getter
@Setter
@AllArgsConstructor
public class ParticipantDTO {

    private Integer id;
    private CreditCard creditCard;
    private Integer userId;

    public ParticipantDTO(){}
}
