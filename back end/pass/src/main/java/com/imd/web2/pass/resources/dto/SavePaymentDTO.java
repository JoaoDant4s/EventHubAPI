package com.imd.web2.pass.resources.dto;

import com.imd.web2.pass.model.PaymentType;

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
