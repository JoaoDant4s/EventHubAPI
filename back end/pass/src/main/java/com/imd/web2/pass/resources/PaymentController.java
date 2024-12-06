package com.imd.web2.pass.resources;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import com.imd.web2.pass.resources.dto.SavePaymentDTO;
import com.imd.web2.pass.resources.exceptions.InvalidParameterException;
import com.imd.web2.pass.resources.exceptions.NoTicketDaysLinkedInTicketException;
import com.imd.web2.pass.resources.exceptions.NotFoundException;
import com.imd.web2.pass.resources.exceptions.NullParameterException;
import com.imd.web2.pass.model.Payment;
import com.imd.web2.pass.resources.dto.PaymentDTO;
import com.imd.web2.pass.services.IPaymentService;

import java.util.UUID;

@RestController
@RequestMapping("api/payment")
public class PaymentController {

    @Autowired
    private IPaymentService paymentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentDTO savePayment(@Valid @RequestBody SavePaymentDTO savePaymentDTO) {
        try {
            Payment payment = paymentService.savePayment(fromSaveDTO(savePaymentDTO), savePaymentDTO.getTicketToPayDTO().getId());
            return toDTO(payment);
        } catch (NullParameterException | InvalidParameterException | NoTicketDaysLinkedInTicketException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/{paymentId}")
    public PaymentDTO getPayment(@PathVariable UUID paymentId) {
        try {
            Payment payment = paymentService.getPaymentById(paymentId).orElseThrow(() -> new NotFoundException("Não existe um pagamento com o id informado"));
            return toDTO(payment);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    private Payment fromSaveDTO(SavePaymentDTO dto) {
        Payment payment = new Payment();
        payment.setPaymentType(dto.getPaymentType());
        return payment;
    }

    private PaymentDTO toDTO(Payment payment) {
        PaymentDTO dto = new PaymentDTO();
        dto.setId(payment.getId());
        dto.setDate(payment.getDate());
        dto.setTotalAmount(payment.getTotalAmount());
        dto.setPaymentType(payment.getPaymentType());
        return dto;
    }
}
