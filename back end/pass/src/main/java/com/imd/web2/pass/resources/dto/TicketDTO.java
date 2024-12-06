package com.imd.web2.pass.resources.dto;

import com.imd.web2.pass.model.Payment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TicketDTO {
    private Integer id;
    private TicketTypeDTO ticketTypeDTO;
    private String paymentStatus;
    private Payment payment;
}
