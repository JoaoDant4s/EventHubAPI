package com.imd.web2.pass.resources.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.imd.web2.pass.model.PaymentType;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDTO {
    private UUID id;
    private LocalDateTime date;
    private BigDecimal totalAmount;
    private PaymentType paymentType;
}
